package org.opensrp.web.controller;

import static ch.lambdaj.collection.LambdaCollections.with;
import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.OPENSRP_IDENTIFIER;
import static org.opensrp.common.AllConstants.Form.STOCK;
import static org.opensrp.common.AllConstants.Form.BirthOutcome_Handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Client;
import org.opensrp.api.domain.Event;
import org.opensrp.connector.BahmniOpenmrsConnector;
import org.opensrp.connector.OpenmrsConnector;
import org.opensrp.connector.openmrs.service.BahmniPatientService;
import org.opensrp.connector.openmrs.service.EncounterService;
import org.opensrp.connector.openmrs.service.HouseholdService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.connector.openmrs.service.PatientService;
import org.opensrp.domain.IdentifierMaping;
import org.opensrp.domain.Multimedia;
import org.opensrp.dto.form.FormSubmissionDTO;
import org.opensrp.dto.form.MultimediaDTO;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.service.FormSubmissionConverter;
import org.opensrp.form.service.FormSubmissionService;
import org.opensrp.register.mcare.OpenSRPScheduleConstants.OpenSRPEvent;
import org.opensrp.register.mcare.service.HHService;
import org.opensrp.repository.AllClients;
import org.opensrp.repository.IndetifierMapingRepository;
import org.opensrp.repository.MultimediaRepository;
import org.opensrp.scheduler.SystemEvent;
import org.opensrp.scheduler.TaskSchedulerService;
import org.opensrp.service.MultimediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import ch.lambdaj.function.convert.Converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
public class FormSubmissionController {
	
	private static Logger logger = LoggerFactory.getLogger(FormSubmissionController.class.toString());
	
	private FormSubmissionService formSubmissionService;
	
	private TaskSchedulerService scheduler;
	
	private EncounterService encounterService;
	
	private OpenmrsConnector openmrsConnector;
	
	private PatientService patientService;
	
	private BahmniOpenmrsConnector bahmniOpenmrsConnector;
	
	private BahmniPatientService bahmniPatientService;
	
	private HouseholdService householdService;
	
	private HHService hhService;
	
	private OpenmrsUserService openmrsUserService;
	
	private MultimediaService multimediaService;
	
	private MultimediaRepository multimediaRepository;
	
	private IndetifierMapingRepository bahmniIdRepository;
	private  AllClients allClients;
	@Autowired
	public FormSubmissionController(FormSubmissionService formSubmissionService, TaskSchedulerService scheduler,
	    EncounterService encounterService, OpenmrsConnector openmrsConnector, PatientService patientService,
	    BahmniOpenmrsConnector bahmniOpenmrsConnector, BahmniPatientService bahmniPatientService,
	    HouseholdService householdService, MultimediaService multimediaService, OpenmrsUserService openmrsUserService,
	    MultimediaRepository multimediaRepository, IndetifierMapingRepository bahmniIdRepository,AllClients allClients) {
		this.formSubmissionService = formSubmissionService;
		this.scheduler = scheduler;
		
		this.encounterService = encounterService;
		this.openmrsConnector = openmrsConnector;
		this.bahmniOpenmrsConnector = bahmniOpenmrsConnector;
		this.bahmniPatientService = bahmniPatientService;
		this.patientService = patientService;
		this.householdService = householdService;
		this.hhService = hhService;
		this.openmrsUserService = openmrsUserService;
		this.multimediaService = multimediaService;
		this.multimediaRepository = multimediaRepository;
		this.bahmniIdRepository = bahmniIdRepository;
		this.allClients = allClients;
	}
	
	@RequestMapping(method = GET, value = "/form-submissions")
	@ResponseBody
	private List<FormSubmissionDTO> getNewSubmissionsForANM(@RequestParam("anm-id") String anmIdentifier,
	                                                        @RequestParam("timestamp") Long timeStamp,
	                                                        @RequestParam(value = "batch-size", required = false) Integer batchSize) {
		List<FormSubmission> newSubmissionsForANM = formSubmissionService.getNewSubmissionsForANM(anmIdentifier, timeStamp,
		    batchSize);
		return with(newSubmissionsForANM).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
			
			@Override
			public FormSubmissionDTO convert(FormSubmission submission) {
				return FormSubmissionConverter.from(submission);
			}
		});
	}
	
	@RequestMapping(method = GET, value = "/all-form-submissions")
	@ResponseBody
	private List<FormSubmissionDTO> getAllFormSubmissions(@RequestParam("timestamp") Long timeStamp,
	                                                      @RequestParam(value = "batch-size", required = false) Integer batchSize) {
		List<FormSubmission> allSubmissions = formSubmissionService.getAllSubmissions(timeStamp, batchSize);
		return with(allSubmissions).convert(new Converter<FormSubmission, FormSubmissionDTO>() {
			
			@Override
			public FormSubmissionDTO convert(FormSubmission submission) {
				return FormSubmissionConverter.from(submission);
			}
		});
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = POST, value = "/form-submissions")
	public ResponseEntity<HttpStatus> submit(@RequestBody List<FormSubmissionDTO> formSubmissionsDTO) {
		boolean flag = false;
		try {
			if (formSubmissionsDTO.isEmpty()) {
				return new ResponseEntity<>(BAD_REQUEST);
			}
			
			try {
				// //////TODO MAIMOONA : SHOULD BE IN EVENT but event needs to
				// be moved to web so for now kept here
				String json = new Gson().toJson(formSubmissionsDTO);
				logger.info("Submission::" + json);
				List<FormSubmissionDTO> formSubmissions = new Gson().fromJson(json,
				    new TypeToken<List<FormSubmissionDTO>>() {}.getType());
				
				List<FormSubmission> fsl = with(formSubmissions).convert(new Converter<FormSubmissionDTO, FormSubmission>() {
					
					@Override
					public FormSubmission convert(FormSubmissionDTO submission) {
						return FormSubmissionConverter.toFormSubmission(submission);
					}
				});
				
				String p;
				for (FormSubmission formSubmission : fsl) {
					if (openmrsConnector.isOpenmrsForm(formSubmission)) {
						if(formSubmission.formName().equalsIgnoreCase(STOCK)){
							logger.info("Stock submission not for openmrs.....");
							break;
						}
						p = getBahmniId(formSubmission.entityId());
						
						if (p != null  ) { // HO
							logger.info("Existing patient found into openMRS with id : " + p 
							        + "/***********************************************************************/");
							Event e;							
							Map<String, Map<String, Object>> dep;
							dep = bahmniOpenmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
							if (dep.size() > 0) { // HOW(n)								
								logger.info("Dependent client exist into formsubmission /***********************************************************************/ ");
								for (Map<String, Object> cm : dep.values()) {
									Client c = (Client) cm.get("client");
									if(this.getClient(c.getBaseEntityId())){										
										String getGeneratedIdForChild = bahmniPatientService.generateID();
										logger.info("Generating ID to openMRS/***********************************************************************:"
											                + getGeneratedIdForChild);
										this.createIdentifierMaping(c.getBaseEntityId(), getGeneratedIdForChild);
										logger.info(""+bahmniPatientService.createPatient((Client) cm.get("client"), getGeneratedIdForChild));
										logger.info(""+encounterService.createEncounter((Event) cm.get("event"), getGeneratedIdForChild));
									}
									
								}
							}else{
								e = bahmniOpenmrsConnector.getEventFromFormSubmission(formSubmission);
								System.out.println("Creates encounter for client id: " + e.getBaseEntityId());
								System.out.println(encounterService.createEncounter(e, p));
							}
							
						}else {
							
							
							
							Map<String, Map<String, Object>> dep;
							dep = bahmniOpenmrsConnector.getDependentClientsFromFormSubmission(formSubmission);
							if (dep.size() > 0) { // HnW(n)
								
								if(this.getClient(formSubmission.entityId())){
									String getGeneratedIdForPatientWhichHasMember = bahmniPatientService.generateID();
									logger.info("Generating ID to openMRS/***********************************************************************:"
									                + getGeneratedIdForPatientWhichHasMember);
									this.createIdentifierMaping(formSubmission.entityId(), getGeneratedIdForPatientWhichHasMember);
									logger.info("Dependent client exist into formsubmission /***********************************************************************/ ");
									Client hhClient = bahmniOpenmrsConnector.getClientFromFormSubmission(formSubmission);
									logger.info(""+bahmniPatientService.createPatient(hhClient, getGeneratedIdForPatientWhichHasMember));
									Event e = bahmniOpenmrsConnector.getEventFromFormSubmission(formSubmission);
									System.out.println(encounterService.createEncounter(e, getGeneratedIdForPatientWhichHasMember));
								}
								for (Map<String, Object> cm : dep.values()) {
									Client c = (Client) cm.get("client");
									if(this.getClient(c.getBaseEntityId())){
										String getGeneratedIdForMember = bahmniPatientService.generateID();									
										this.createIdentifierMaping(c.getBaseEntityId(), getGeneratedIdForMember);
										logger.info(""+bahmniPatientService.createPatient((Client) cm.get("client"), getGeneratedIdForMember));
										
										logger.info("E:" + (Event) cm.get("event"));
										logger.info(""+encounterService.createEncounter((Event) cm.get("event"), getGeneratedIdForMember));
									}
								}
								
								
							} else {// HnW(0)
								
								if(this.getClient(formSubmission.entityId())){
									String getGeneratedIdForPatientWhichHasNoMember = bahmniPatientService.generateID();
									logger.info("Generating ID to openMRS/***********************************************************************:"
									                + getGeneratedIdForPatientWhichHasNoMember);
									this.createIdentifierMaping(formSubmission.entityId(), getGeneratedIdForPatientWhichHasNoMember);
									logger.info("Patient and Dependent client not exist into Bahmni openmrs /***********************************************************************/ ");
									Client c = bahmniOpenmrsConnector.getClientFromFormSubmission(formSubmission);
									logger.info(""+bahmniPatientService.createPatient(c, getGeneratedIdForPatientWhichHasNoMember));
									Event e = bahmniOpenmrsConnector.getEventFromFormSubmission(formSubmission);
									logger.info(""+encounterService.createEncounter(e, getGeneratedIdForPatientWhichHasNoMember));
								}
							}
						}
					}
				}				
				
			}
			catch (Exception e) {				
				logger.error(e.getMessage());
			}
			logger.debug(format("Added Form submissions to queue.\nSubmissions: {0}", formSubmissionsDTO));
		}
		catch (Exception e) {
			logger.error(format("Form submissions processing failed with exception {0}.\nSubmissions: {1}", e,
			    formSubmissionsDTO));
			flag = true;
		}
		
		scheduler.notifyEvent(new SystemEvent<>(OpenSRPEvent.FORM_SUBMISSION, formSubmissionsDTO));
		if(flag){
			return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
		}
		else{			
			return new ResponseEntity<>(CREATED);
		}
		
	}
	
	@RequestMapping(method = GET, value = "/entity-id")
	@ResponseBody
	public ResponseEntity<String> getEntityIdForBRN(@RequestParam("brn-id") List<String> brnIdList) {
		return new ResponseEntity<>(new Gson().toJson(hhService.getEntityIdBybrnId(brnIdList)), OK);
	}
	
	@RequestMapping(method = GET, value = "/user-location")
	@ResponseBody
	public ResponseEntity<String> getUserByLocation(@RequestParam("location-name") String locationName) {
		JSONObject usersAssignedToLocation = null;
		String userName = "";
		try {
			usersAssignedToLocation = openmrsUserService.getTeamMemberByLocation(locationName);
			userName = usersAssignedToLocation.getJSONArray("results").getJSONObject(0).getJSONObject("user")
			        .getString("username");
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(userName, OK);
	}
	
	@RequestMapping(headers = { "Accept=application/json" }, method = GET, value = "/multimedia-file")
	@ResponseBody
	public List<MultimediaDTO> getFiles(@RequestParam("anm-id") String providerId) {
		
		List<Multimedia> allMultimedias = multimediaService.getMultimediaFiles(providerId);
		
		return with(allMultimedias).convert(new Converter<Multimedia, MultimediaDTO>() {
			
			@Override
			public MultimediaDTO convert(Multimedia md) {
				return new MultimediaDTO(md.getCaseId(), md.getProviderId(), md.getContentType(), md.getFilePath(), md
				        .getFileCategory());
			}
		});
	}
	
	@RequestMapping(headers = { "Accept=multipart/form-data" }, method = POST, value = "/multimedia-file")
	public ResponseEntity<String> uploadFiles(@RequestParam("anm-id") String providerId,
	                                          @RequestParam("entity-id") String entityId,
	                                          @RequestParam("content-type") String contentType,
	                                          @RequestParam("file-category") String fileCategory,
	                                          @RequestParam("file") MultipartFile file) throws ClientProtocolException,
	    IOException {
		MultimediaDTO multimediaDTO = new MultimediaDTO(entityId, providerId, contentType, null, fileCategory);
		String status = multimediaService.saveMultimediaFile(multimediaDTO, file);
		
		if (status.equals("success")) {
			Multimedia multimedia = multimediaRepository.findByCaseId(entityId);
			patientService.patientImageUpload(multimedia);
		}
		return new ResponseEntity<>(new Gson().toJson(status), OK);
	}
	
	private void createIdentifierMaping(String entityId, String idGen) {
		IdentifierMaping bahmniIdgen = new IdentifierMaping();
		bahmniIdgen.setGenId(idGen);
		bahmniIdgen.setEntityId(entityId);
		try {			
			bahmniIdRepository.add(bahmniIdgen);
			
		}
		catch (Exception ee) {
			logger.info("" + ee.getMessage());
			
		}
	}
	
	private boolean getClient(String entityid){
		if(allClients.findByBaseEntityId(entityid) ==null){
			return true;
		}else{
			return false;
		}
	}
	private String getBahmniId(String entityId) {
		try {
			IdentifierMaping id = bahmniIdRepository.findByentityId(entityId);
			return id.getGenId();
		}
		catch (Exception ee) {
			logger.info("Bahmni id finding : " + ee.getMessage());
		}
		
		return null;
	}
}

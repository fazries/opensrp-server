package org.opensrp.domain;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;


@TypeDiscriminator("doc.type === 'Vaccine'")
public class Vaccine extends MotechBaseDataObject{
	
	 /**
     * 
     */
    private static final long serialVersionUID = 1L;
		@JsonProperty
	    private String health_assistant;
	    @JsonProperty
	    private String clientId;
	    @JsonProperty
	    private String actionId;	    
	    @JsonProperty
	    private String beneficiaryType;
	    @JsonProperty
	    private String vaccineName;
	    @JsonProperty
	    private DateTime scheduleDate;
	    @JsonProperty
	    private DateTime expiredDate;
	    @JsonProperty
	    private String status;
	    @JsonProperty
	    private String missedCount;
	    @JsonProperty
	    private Date createdDate;
	    @JsonProperty
	    private DateTime executionDate;
	    public Vaccine(){
	    	
	    }
		public Vaccine(String health_assistant, String clientId, String actionId, String beneficiaryType,
            String vaccineName, DateTime scheduleDate, DateTime expiredDate, String status, String missedCount,
            Date createdDate, DateTime executionDate) {
	        
	        this.health_assistant = health_assistant;
	        this.clientId = clientId;
	        this.actionId = actionId;
	        this.beneficiaryType = beneficiaryType;
	        this.vaccineName = vaccineName;
	        this.scheduleDate = scheduleDate;
	        this.expiredDate = expiredDate;
	        this.status = status;
	        this.missedCount = missedCount;
	        this.createdDate = createdDate;
	        this.executionDate = executionDate;
        }
		
        public String getHealth_assistant() {
        	return health_assistant;
        }
		
        public String getClientId() {
        	return clientId;
        }
		
        public String getActionId() {
        	return actionId;
        }
		
        public String getBeneficiaryType() {
        	return beneficiaryType;
        }
		
        public String getVaccineName() {
        	return vaccineName;
        }
		
        public DateTime getScheduleDate() {
        	return scheduleDate;
        }
		
        public DateTime getExpiredDate() {
        	return expiredDate;
        }
		
        public String getStatus() {
        	return status;
        }
		
        public String getMissedCount() {
        	return missedCount;
        }
		
        public Date getCreatedDate() {
        	return createdDate;
        }
		
        public DateTime getExecutionDate() {
        	return executionDate;
        }
		
        public void setHealth_assistant(String health_assistant) {
        	this.health_assistant = health_assistant;
        }
		
        public void setClientId(String clientId) {
        	this.clientId = clientId;
        }
		
        public void setActionId(String actionId) {
        	this.actionId = actionId;
        }
		
        public void setBeneficiaryType(String beneficiaryType) {
        	this.beneficiaryType = beneficiaryType;
        }
		
        public void setVaccineName(String vaccineName) {
        	this.vaccineName = vaccineName;
        }
		
        public void setScheduleDate(DateTime scheduleDate) {
        	this.scheduleDate = scheduleDate;
        }
		
        public void setExpiredDate(DateTime expiredDate) {
        	this.expiredDate = expiredDate;
        }
		
        public void setStatus(String status) {
        	this.status = status;
        }
		
        public void setMissedCount(String missedCount) {
        	this.missedCount = missedCount;
        }
		
        public void setCreatedDate(Date createdDate) {
        	this.createdDate = createdDate;
        }
		
        public void setExecutionDate(DateTime executionDate) {
        	this.executionDate = executionDate;
        }
		
	    
	
}

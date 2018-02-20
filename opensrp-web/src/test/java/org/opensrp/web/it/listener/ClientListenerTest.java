/**
 * 
 */
package org.opensrp.web.it.listener;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.repository.AllCamp;
import org.opensrp.repository.AllClients;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.web.listener.RapidproMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author proshanto
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-opensrp-web.xml" })
public class ClientListenerTest {
	
	@Autowired
	private AllClients allClients;
	
	@Autowired
	private AllActions allActions;
	
	@Autowired
	private RapidproMessageListener rapidProMessageListener;
	
	@Autowired
	private AllCamp allCamp;
	
	@Before
	public void setup() throws IOException {
		//allClients.removeAll();
		//	allActions.removeAll();
	}
	
	@Ignore
	@Test
	public void testFetchClient() throws JSONException {
		
		/*		Client child = (Client) new Client("127").withFirstName("foomm").withGender("female").withLastName("bae ff")
				        .withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
				
				List<String> motherRelationshipsList = new ArrayList<>();
				motherRelationshipsList.add("130");
				Map<String, List<String>> motherRelationships = new HashMap<>();
				motherRelationships.put("mother", motherRelationshipsList);
				child.setRelationships(motherRelationships);
				
				allClients.add(child);
				
				Client mother = (Client) new Client("130").withFirstName("foorrr").withGender("female").withLastName("bae ff")
				        .withBirthdate(new DateTime(), false).withDateCreated(new DateTime());
				
				Map<String, Object> motherAttributes = new HashMap<>();
				
				motherAttributes.put("phoneNumber", "01711082537");
				motherAttributes.put("nationalId", "76543222349775");
				motherAttributes.put("spouseName", "Dion");
				mother.setAttributes(motherAttributes);
				allClients.add(mother);
				Action normalAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", normal,
				    DateTime.now(), DateTime.now().plusDays(3)));
				Action upcominglAction = new Action("127", "ANM 1", ActionData.createAlert("child", "opv", "opv0", upcoming,
				    DateTime.now(), DateTime.now().plusDays(3)));
				allActions.add(normalAction);
				allActions.add(upcominglAction);*/
		rapidProMessageListener.fetchClient();
		
		//rapidProMessageListener.campAnnouncementListener("raihan");
		
	}
	
	@Test
	public void testAddress() {
		
		System.err.println("client address: "
		        + allClients.findByBaseEntityId("03cb7e03-35f3-455c-8047-ac7e5aa0febd").getAddresses().get(0)
		                .getAddressField("address3"));
	}
	
	@Ignore
	@Test
	public void testgetAgeOfChild() {
		System.err.println("getAgeOfChild: " + getAgeOfChild(new DateTime().minusMonths(23).toDate()));
		
	}
	
	private int getAgeOfChild(Date dateTime) {
		System.err.println("getAgeOfChild: " + dateTime);
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		int age = 0;
		
		dob.setTime(dateTime);
		
		if (dob.after(now)) {
			throw new IllegalArgumentException("Can't be born in the future");
		}
		
		age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
		if (now.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		
		return age;
	}
	
}

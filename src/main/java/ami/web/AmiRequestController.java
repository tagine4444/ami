package ami.web;

import java.io.IOException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.application.services.AmiRequestService;
import ami.domain.model.amicase.Amendment;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.services.AmiServices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class AmiRequestController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	public static final String UPLOAD_DIR = "uploads";
	
	@Autowired
	private AmiRequestService amiRequestService;
	
	@Autowired
	private AmiServices amiServices;
	
	@Autowired
	private AmiUserRepository userService;
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/switchcasetoinprogress", method = RequestMethod.GET)
	@ResponseBody
	public String switchCaseToInProgressAndReturnCase(@RequestParam String caseNumber) throws JsonProcessingException {
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.switchCaseToInProgress(caseNumber, userName, new DateTime());
		return amiRequestService.findAmiRequest(caseNumber);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/switchcasetoreadyforreview", method = RequestMethod.POST)
	@ResponseBody
	public void switchCaseToReadyForReview(@RequestBody String data) throws JsonProcessingException {
			
		DBObject dbObject = (DBObject)JSON.parse(data);
			final String caseNumber = (String) dbObject.get("caseNumber");
			
//			final String radiographicInterpretation = (String) dbObject.get("radiographicInterpretation");
//			final String radiographicImpression = (String) dbObject.get("radiographicImpression");
//			final String recommendation = (String) dbObject.get("recommendation");
			
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.switchCaseToReadyForReview(caseNumber, userName, new DateTime());
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/updateRadiographicInterpretation", method = RequestMethod.POST)
	@ResponseBody
	public void updateRadiographicInterpretation(@RequestBody String data) throws JsonProcessingException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String radiographicInterpretation = (String) dbObject.get("radiographicInterpretation");
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.updateRadiographicInterpretation(caseNumber, userName, new DateTime(), radiographicInterpretation);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/updateRadiographicImpression", method = RequestMethod.POST)
	@ResponseBody
	public void updateRadiographicImpression(@RequestBody String data) throws JsonProcessingException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String radiographicImpression = (String) dbObject.get("radiographicImpression");
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.updateRadiographicImpression(caseNumber, userName, new DateTime(), radiographicImpression);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/updateRecommendation", method = RequestMethod.POST)
	@ResponseBody
	public void updateRecommendation(@RequestBody String data) throws JsonProcessingException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String recommendation = (String) dbObject.get("recommendation");
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.updateRecommendation(caseNumber, userName, new DateTime(), recommendation);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/closecase",method = RequestMethod.POST)
	@ResponseBody
	public void closeCase(@RequestBody String data) throws JsonProcessingException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.closeCase(caseNumber, userName, new DateTime());
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/doaccounting",method = RequestMethod.POST)
	@ResponseBody
	public String doAccounting(@RequestBody String data) throws JsonProcessingException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.doAccounting(caseNumber, userName, new DateTime());
		return amiRequestService.findCasesPendingAccounting();
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/casespendingaccounting",method = RequestMethod.GET)
	@ResponseBody
	public String findCasesPendingAccounting() throws JsonProcessingException {
		return amiRequestService.findCasesPendingAccounting();
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/addAmendment",method = RequestMethod.POST)
	@ResponseBody
	public void addAmendmentAdmin(@RequestBody String data) throws JsonProcessingException {
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView anAmiUserView = userService.findAmiUser(userName);
		
		DateTime creationDate = new DateTime();  
		String firstName = anAmiUserView.getAmiUser().getFirstName(); 
		String lastName  =  anAmiUserView.getAmiUser().getLastName();
		
		Amendment amendment = new Amendment(creationDate, userName, firstName, lastName);
		amiServices.amend(caseNumber, amendment);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequest(@RequestParam String requestNumber) throws JsonProcessingException {
		return amiRequestService.findAmiRequest(requestNumber);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/byanimals", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByAnimalName(@RequestParam String animalName) throws JsonProcessingException {
		return amiRequestService.findAmiRequestByAnimalName(animalName) ;
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/byclientlastname", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByClientLastName(@RequestParam String clientlastname) throws JsonProcessingException {
		return amiRequestService.findAmiRequestByClientLastName(clientlastname);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/byreqdaterange", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestBySubmittedDateRange(@RequestParam String date1, @RequestParam String date2) throws JsonProcessingException {
		
		String useName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView anAmiUserView = userService.findAmiUser(useName);
		final String hospitalId = anAmiUserView.getHospitalId();
		return amiRequestService.findAmiRequestBySubmittedDateRange( hospitalId, date1,   date2);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/bylastnrecords", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByLast50Records() throws JsonProcessingException {
		SecurityContextHolder.getContext().getAuthentication().getName();
		
		String useName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView anAmiUserView = userService.findAmiUser(useName);
		return amiRequestService.findAmiRequestByLast50Records(anAmiUserView.getHospitalId());
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/pending", method = RequestMethod.GET)
	@ResponseBody
	public String findPendigAmiRequests() throws JsonProcessingException {
		return amiRequestService.findPendigAmiRequests();
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/pending/allhospitals", method = RequestMethod.GET)
	@ResponseBody
	public String findPendigAmiRequestsForAllHospitals() throws JsonProcessingException {
		return amiRequestService.findPendigAmiRequestsForAllHospitals();
	}
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/pendingreview/allhospitals", method = RequestMethod.GET)
	@ResponseBody
	public String findCasesPendigReviewForAllHospitals() throws JsonProcessingException {
		//TODO change this method to findCasesPendigReviewForAllHospitals() in mongo
		return amiRequestService.findCaesPendingRevewForAllHospitals();
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/draft", method = RequestMethod.GET)
	@ResponseBody
	public String findDraftAmiRequests() throws JsonProcessingException {
		
		return amiRequestService.findDraftAmiRequests();
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/uploadedfiles", method = RequestMethod.GET)
	@ResponseBody
	public String getUploadedFiles(@RequestParam String requestNumber) throws JsonProcessingException {
		
		return amiRequestService.getUploadedFiles(requestNumber);
	}
	
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String submitAmiRequestToRadiologist(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		//String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String contract = (String) dbObject.get("contract");
		final String accountSize = (String) dbObject.get("accountSize");
		AmiRequest amiRequest1 = objectMapper.readValue(amiRequest.toString(), AmiRequest.class);
		
		return amiRequestService.submitAmiRequestToRadiologist(caseNumber, amiRequest1 ,userName , hospitalName,hospitalId,contract,accountSize);
		
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amidraftrequest", method = RequestMethod.POST)
	@ResponseBody
	public String createCaseAsDraft(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
	
		final String jsonString = amiRequest.toString();
		System.out.println(jsonString);
		AmiRequest req = objectMapper.readValue(jsonString, AmiRequest.class);
		
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String contract = (String) dbObject.get("contract");
		final String accountSize = (String) dbObject.get("accountSize");
		
		return amiRequestService.createCaseAsDraft(caseNumber,req ,userName , hospitalName, hospitalId,contract,accountSize);
		
	}
	
	
}

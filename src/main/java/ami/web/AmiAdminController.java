package ami.web;

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
import ami.application.services.impl.AmendmentNotificationService;
import ami.domain.model.amicase.Amendment;
import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;
import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.services.AmiServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class AmiAdminController {


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
	private AmendmentNotificationService amendmentNotificationService;
	
	@Autowired
	private AmiCaseNumberGeneratorRepository amiCaseNumberGeneratorRepository;
	
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/amirequest/bylastnrecords", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByLast50Records(@RequestParam String limit) throws JsonProcessingException {
		int numOfCases = Integer.valueOf(limit);
		return amiRequestService.findAmiRequestByLast50RecordsAdmin(numOfCases);
	}
	
	
	//=======================================
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/amirequest", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequest(@RequestParam String requestNumber) throws JsonProcessingException {
		return amiRequestService.findAmiRequest(requestNumber, true);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"')  or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/amirequest/byanimals", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByAnimalName(@RequestParam String animalName) throws JsonProcessingException {
		return amiRequestService.findAmiRequestByAnimalName(animalName, true) ;
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/amirequest/byclientlastname", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestByClientLastName(@RequestParam String clientlastname) throws JsonProcessingException {
		return amiRequestService.findAmiRequestByClientLastName(clientlastname);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/amirequest/byreqdaterange", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequestBySubmittedDateRange(@RequestParam String date1, @RequestParam String date2) throws JsonProcessingException {
		
		String useName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView anAmiUserView = userService.findAmiUser(useName);
		final String hospitalId = anAmiUserView.getHospitalId();
		return amiRequestService.findAmiRequestBySubmittedDateRange( hospitalId, date1,   date2);
	}
	
	//===================================
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/switchcasetoinprogress", method = RequestMethod.GET)
	@ResponseBody
	public String switchCaseToInProgressAndReturnCase(@RequestParam String caseNumber) throws JsonProcessingException {
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		amiServices.switchCaseToInProgress(caseNumber, userName, new DateTime());
		return amiRequestService.findAmiRequest(caseNumber, true);
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
	@RequestMapping(value = "/ami/amiadmin/amendedcases",method = RequestMethod.GET)
	@ResponseBody
	public String getAmendmentNotificationsForAdmin() throws JsonProcessingException {
		return amendmentNotificationService.getAmendmentNotificationsForAdmin();
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin/addAmendmentAdmin",method = RequestMethod.POST)
	@ResponseBody
	public String addAmendmentAdmin(@RequestBody String data) throws JsonProcessingException {
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String caseNumber = (String) dbObject.get("caseNumber");
		final String newAmendment = (String) dbObject.get("newAmendment");
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView anAmiUserView = userService.findAmiUser(userName);
		
		DateTime creationDate = new DateTime();  
		String firstName   = anAmiUserView.getAmiUser().getFirstName(); 
		String lastName    =  anAmiUserView.getAmiUser().getLastName();
		String occupation  = anAmiUserView.getAmiUser().getOccupation();
		String hospitalName = anAmiUserView.getHospitalName();
		String hospitalId = anAmiUserView.getHospitalId();
		
		
		int nextAmendmentId = amiCaseNumberGeneratorRepository.getNextAmendmentId();
		Amendment amendment = new Amendment(nextAmendmentId, newAmendment, creationDate, userName, firstName, lastName, 
				occupation,hospitalName, hospitalId,false);
		amiServices.amend(caseNumber, amendment);
		return amiRequestService.findAmiAmendments(caseNumber, true);
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

}

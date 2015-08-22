package ami.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.application.services.amirequest.AmiRequestService;
import ami.application.views.AmiRequestView;
import ami.domain.amirequest.AmiRequest;
import ami.domain.security.AmiAuthtorities;

import com.fasterxml.jackson.core.JsonProcessingException;
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
	private ObjectMapper objectMapper;

	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.GET)
	@ResponseBody
	public String findAmiRequest(@RequestParam String requestNumber) throws JsonProcessingException {
		AmiRequestView amiRequestView = amiRequestService.findAmiRequest(requestNumber);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSave(@RequestBody String data) {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		
		amiRequestService.createAmiRequest(amiRequest.toString() ,userName , hospitalName);
		
		return "{}";
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amidraftrequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSaveAsDraft(@RequestBody String data) {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
		AmiRequest req = null;
		try {
			final String jsonString = amiRequest.toString();
			System.out.println(jsonString);
			 req = objectMapper.readValue(jsonString, AmiRequest.class);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		
		amiRequestService.saveAmiRequestAsDraft(amiRequest.toString() ,userName , hospitalName);
		
		return "{}";
	}
}

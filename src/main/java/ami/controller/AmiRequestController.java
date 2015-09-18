package ami.controller;

import java.io.IOException;
import java.util.List;

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
import ami.domain.amirequest.FileUploadInfo;
import ami.domain.security.AmiAuthtorities;

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
	@RequestMapping(value = "/ami/amicusthome/amirequest/pending", method = RequestMethod.GET)
	@ResponseBody
	public String findPendigAmiRequests() throws JsonProcessingException {
		
		List<AmiRequestView> amiRequestView = amiRequestService.findPendingAmiRequest();
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest/uploadedfiles", method = RequestMethod.GET)
	@ResponseBody
	public String getUploadedFiles(@RequestParam String requestNumber) throws JsonProcessingException {
		
		AmiRequestView amiRequestView = amiRequestService.findAmiRequest( requestNumber);
		List<FileUploadInfo> fileUploads = amiRequestView.getFileUploads();
		
		String fileUploadsString = objectMapper.writeValueAsString(fileUploads);
		return fileUploadsString;
	}
	
	
	
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSave(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		//String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		final String hospitalId = (String) dbObject.get("hospitalId");
		
		AmiRequest amiRequest1 = objectMapper.readValue(amiRequest.toString(), AmiRequest.class);
		
		//amiRequestService.createAmiRequest(amiRequest.toString() ,userName , hospitalName);
		amiRequestService.createAmiRequest(amiRequest1 ,userName , hospitalName,hospitalId);
		
		return "{}";
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amidraftrequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSaveAsDraft(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
	
		final String jsonString = amiRequest.toString();
		System.out.println(jsonString);
		AmiRequest req = objectMapper.readValue(jsonString, AmiRequest.class);
		
		
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		final String hospitalId = (String) dbObject.get("hospitalId");
		
		String requestNumber = amiRequestService.saveAmiRequestAsDraft(req ,userName , hospitalName, hospitalId);
		
		return "{\"requestNumber\": \""+requestNumber+"\"}";
	}
}

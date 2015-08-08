package ami.controller;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.application.services.amirequest.AmiRequestService;
import ami.axon.CommandGenerator;
import ami.model.users.AmiAuthtorities;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class LoginController {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	public static final String UPLOAD_DIR = "uploads";
	
	@Autowired
	private CommandGateway commandGateway;
	@Autowired
	private AmiRequestService amiRequestService;
	
	
	
	@RequestMapping(value = "/ami/axon", method = RequestMethod.GET)
	public String amiLoginGET(Model model) {
		
		CommandGenerator.sendCommands(commandGateway);
		return "";
	}
	
	
	@RequestMapping(value = "/ami/save", method = RequestMethod.GET)
	public String amiSave(Model model) {
		
		Person p = new Person("Joe", 34);
		mongo.insert(p);
		
		p = mongo.findById(p.getId(), Person.class);
		log.debug("Found "+p);
		System.out.println("found "+p);
		
		return "redirect:amicusthome";
	}

	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSave(@RequestBody String data) {
	//public String amiRequestSave(@RequestBody String amiRequest,@RequestBody String userName,  @RequestBody String hospitalName ) {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject amiRequest = (BasicDBObject) dbObject.get("amiRequest");
		final String userName = (String) dbObject.get("userName");
		final String hospitalName = (String) dbObject.get("hospitalName");
		
		amiRequestService.createAmiRequest(amiRequest.toString() ,userName , hospitalName);
		
		//mongo.save(newAmiRequest, "request");
//		mongo.save(dbObject, "request");
//		System.out.println(dbObject.get("_id"));
//		Object object = dbObject.get("_id");
//		
//		
//		Query query2 = new Query();
//		query2.addCriteria(Criteria.where("requestNumber").is(requestNumber));
//		
//		String result = mongo.findOne(query2, String.class,"request");
//		System.out.println(result);
		
		return "{}";
		//return "{\"id\": \" "+object.toString()+" \", \"requestNumber\": \""+requestNumber+"\"}";
	}
	
}

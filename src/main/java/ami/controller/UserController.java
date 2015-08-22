package ami.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.application.services.security.AmiUserService;
import ami.domain.amiusers.AmiUser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private AmiUserService amiUserService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@RequestMapping(value = "/ami/getuserid", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getUserId() throws JsonProcessingException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		AmiUser amiUser = amiUserService.findAmiUser(userName).getAmiUser();
		amiUser.blurPassword();
		
//		AmiUser amiUser = mongo.findOne(
//				query(where("user").is(userName)),AmiUser.class,"amiuser1");
//		//amiUser.blurPassword();
		
		String amiUserString = objectMapper.writeValueAsString(amiUser);
		//String amiUserString = JSON.serialize(amiUser);
		
		log.debug("Found "+amiUserString);
		
		return amiUserString;
			
	}
}

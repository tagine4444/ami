package ami.controller;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.model.users.AmiAdminAuthority;
import ami.model.users.AmiUser;
import ami.model.users.AmiUserAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@RequestMapping(value = "/ami/getuserid", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getUserId() throws JsonProcessingException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		AmiUser amiUser = mongo.findOne(
				query(where("user").is(userName)),AmiUser.class);
		amiUser.blurPassword();
		
		
		String amiUserString = objectMapper.writeValueAsString(amiUser);
		//String amiUserString = JSON.serialize(amiUser);
		
		log.debug("Found "+amiUserString);
		
//		final String result = "{\"userName\": \""+ userName +"\"}";
//		final String result = "{\"userName\": \""+ userName +"\"}";
		return amiUserString;
			
	}

	@RequestMapping(value = "/ami/newuser", method = RequestMethod.GET)
	public String amiSave(Model model) {
		
		List<AmiAdminAuthority> adminList =  new ArrayList<AmiAdminAuthority>();
		AmiAdminAuthority admin = new AmiAdminAuthority();
		adminList.add(admin);
		
		List<AmiUserAuthority> userList =  new ArrayList<AmiUserAuthority>();
		AmiUserAuthority user = new AmiUserAuthority();
		userList.add(user);
		
		AmiUser vet = new AmiUser("vet", "vet","Pet Hospital",userList);
		mongo.insert(vet);
		
		AmiUser chuck = new AmiUser("chuck", "chuck","Animal Medical Imaging", adminList);
		mongo.insert(chuck);
		
		vet = mongo.findById(vet.getId(), AmiUser.class);
		chuck = mongo.findById(chuck.getId(), AmiUser.class);
		log.debug("Found "+vet);
		log.debug("Found "+chuck);
		
		return "redirect:amicusthome";
	}
	
	

	
}

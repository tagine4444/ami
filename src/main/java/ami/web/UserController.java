package ami.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserAuthority;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.amiusers.NewUser;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private AmiUserRepository amiUserService;
	
	@Autowired
	private HospitalRepository hospitalService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@RequestMapping(value = "/amiuser", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String findUserByUserName(@RequestParam String userName) throws JsonProcessingException {
		
		AmiUser amiUser = amiUserService.findAmiUser(userName).getAmiUser();
		amiUser.blurPassword();
		
		String amiUserString = objectMapper.writeValueAsString(amiUser);
		
		log.debug("Found "+amiUserString);
		
		return amiUserString;
			
	}
	
	@RequestMapping(value = "/getuserid", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/hospital", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getHospital() throws JsonProcessingException {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUser amiUser = amiUserService.findAmiUser(userName).getAmiUser();
		String hospitalId = amiUser.getHospitalId();
		HospitalView hospital = hospitalService.findHospital(hospitalId);
		String hospitalString = objectMapper.writeValueAsString(hospital);
		log.debug("Found "+hospitalString);
		return hospitalString;
		
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amicusthome/newuser", method = RequestMethod.POST)
	@ResponseBody
	public String addNewUser(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUser amiUser = amiUserService.findAmiUser(userName).getAmiUser();
		String hospitalId = amiUser.getHospitalId();
		String hospitalName = amiUser.getHospitalName();
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject newUserBasicDBObject = (BasicDBObject) dbObject.get("newUser");
		
		List<GrantedAuthority> userList =  new ArrayList<GrantedAuthority>();
		AmiUserAuthority role = new AmiUserAuthority();
		userList.add(role);
		
		NewUser newUser = objectMapper.readValue(newUserBasicDBObject.toString(), NewUser.class);
		
		boolean userAlreadyExists = true;
		try {
			amiUserService.findAmiUser(newUser.getUserName());
		} catch (NoDataFoundException e) {
			userAlreadyExists = false;
		}
		
		if(userAlreadyExists){
			throw new DataIntegrityViolationException("User Already exists");
		}
		
		
		AmiUser newAmiUser = new AmiUser(newUser, hospitalName, hospitalId,userList);
		
		
		amiUserService.createAmiUser(hospitalId, hospitalName, newAmiUser);

		
		return "{}";
	}
}

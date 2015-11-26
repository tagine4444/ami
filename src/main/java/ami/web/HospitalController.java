package ami.web;

import java.io.IOException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;
import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class HospitalController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private HospitalRepository hospitalService;
	
	@Autowired
	private AmiUserRepository amiUserService;
	@Autowired
	private AmiCaseNumberGeneratorRepository sequenceGenerator;
	
	
	
	@Autowired
	private ObjectMapper objectMapper;

	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadminhome/hospital/setup", method = RequestMethod.POST)
	@ResponseBody
	public void setupNewHospital(@RequestBody String data) throws JsonParseException, JsonMappingException, IOException {
		
		//String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final BasicDBObject masterUser = (BasicDBObject) dbObject.get("masterUser");
		final BasicDBObject hospitalJson = (BasicDBObject) dbObject.get("hospital");
		
		int nextHospitalId = sequenceGenerator.getNextHospitalId();
		String hospitalId = String.valueOf(nextHospitalId);
		Hospital hospital = objectMapper.readValue(hospitalJson.toString(), Hospital.class);
		hospital.init(hospitalId);
		
		hospitalService.createHospital(hospital, new DateTime());
		HospitalView saveHospital = hospitalService.findHospital(hospitalId);
		
		AmiUser amiMasterUser = objectMapper.readValue(masterUser.toString(), AmiUser.class);
		amiMasterUser.initializeMasterUser(hospitalId);
		
		final Hospital myHospital = saveHospital.getHospital();
		final String hospId  = myHospital.getId();
		final String hospName = myHospital.getName();
		
		amiUserService.createAmiUser(hospId, hospName, amiMasterUser);
		
	}
	
}

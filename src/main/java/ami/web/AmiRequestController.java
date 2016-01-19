package ami.web;

import java.io.IOException;

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
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.infrastructure.database.model.HospitalView;
import ami.infrastructure.services.AmiServices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class AmiRequestController {
	

	enum VALID_USER_ACTIONS {updatePwd, updateEmail, updateFirstName, updateLastName, updateOccupation}
	enum VALID_HOSPTIAL_ACTIONS {updatePhones, updateEmails, updateAddresses, updateAcronym , updateNotes, updateContract,updateAccountSize}
	                                                      
	
	
	private static final Logger log = LoggerFactory.getLogger(HospitalController.class);
	
	@Autowired
	private HospitalRepository hospitalService;
	
	
	@Autowired
	private AmiServices amiServices;
	
	@Autowired
	private AmiUserRepository amiUserService;
	
	@Autowired
	private AmiCaseNumberGeneratorRepository sequenceGenerator;
	
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_MASTER_USER+"')")
	@RequestMapping(value = "/ami/amicusthome/hospital/edituser", method = RequestMethod.POST)
	@ResponseBody
	public void editUser(@RequestBody String data) throws IOException {
		

		DBObject dbObject = (DBObject)JSON.parse(data);
		final String newValue = (String) dbObject.get("value");
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String action = (String) dbObject.get("action");
		final String userName = (String) dbObject.get("userName");
		
		try {
			 VALID_USER_ACTIONS.valueOf(action);
		} catch (Exception e) {
			throw new RuntimeException("Master Uer not updated because the action is unknown: " +action );
		}
		
		HospitalView hospitalView = hospitalService.findHospital(hospitalId);
		//final String userName     = hospitalView.getMasterUser().getUser();
		
		if( action.equals(VALID_USER_ACTIONS.updatePwd.name())){
			
			amiServices.updateMasterUserPwd(hospitalId, userName, newValue);
			
		}
		else if (action.equals(VALID_USER_ACTIONS.updateEmail.name())){
			
			amiServices.updateMasterUserEmail(hospitalId, userName, newValue);
		}
		 else if (action.equals(VALID_USER_ACTIONS.updateFirstName.name())){
			 amiServices.updateMasterUserFirstName(hospitalId, userName, newValue);
		}
		else if (action.equals(VALID_USER_ACTIONS.updateLastName.name())){
			
			amiServices.updateMasterUserLastName(hospitalId, userName, newValue);
		}else if (action.equals(VALID_USER_ACTIONS.updateOccupation.name())){
			amiServices.updateMasterUserOccupation(hospitalId, userName, newValue);
		}
		else {
			throw new RuntimeException("Should never be here, but just in case. Master Uer not updated because the action is unknown: " +action );
		}
	}
	
	
}

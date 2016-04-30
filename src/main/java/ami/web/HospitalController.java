package ami.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;
import ami.domain.model.security.AmiAuthtorities;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.factory.HospitalAttributeFactory;
import ami.domain.model.security.hospitals.Address;
import ami.domain.model.security.hospitals.Email;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.domain.model.security.hospitals.Phone;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.database.model.HospitalView;
import ami.infrastructure.services.AmiServices;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class HospitalController {
	
	enum VALID_MASTER_USER_ACTIONS {updatePwd, updateEmail, updateFirstName, updateLastName, updateOccupation}
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

	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospital/setup", method = RequestMethod.POST)
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
		
		amiServices.createHospital(hospital, new DateTime());
		HospitalView saveHospital = hospitalService.findHospital(hospitalId);
		
		AmiUser amiMasterUser = objectMapper.readValue(masterUser.toString(), AmiUser.class);
		amiMasterUser.initializeMasterUser(hospitalId, saveHospital.getHospital().getName());
		
		final Hospital myHospital = saveHospital.getHospital();
		final String hospId  = myHospital.getId();
		final String hospName = myHospital.getName();
		
		amiUserService.createAmiUser(hospId, hospName, amiMasterUser);
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospitalview", method = RequestMethod.GET)
	@ResponseBody
	public String getAllHospitals(Model model) throws JsonProcessingException {
		
		List<HospitalView> hopitalViews = hospitalService.getAllHospitals();
		String speciesString = objectMapper.writeValueAsString(hopitalViews);
		return speciesString;
		
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospitalviewbyhospitalid", method = RequestMethod.GET)
	@ResponseBody
	public String getHospitalById(Model model, @RequestParam(value="hospitalId") String hospitalId) throws JsonProcessingException {
		HospitalView hopitalView = hospitalService.findHospital(hospitalId);
		String speciesString = objectMapper.writeValueAsString(hopitalView);
		return speciesString;
		
	}
	
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospital/updatehospitalcontractoraccount", method = RequestMethod.POST)
	@ResponseBody
	public void updateHospitalContractOrAccountSize(@RequestBody String data) throws IOException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		
		
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String action = (String) dbObject.get("action");
		
		try {
			VALID_HOSPTIAL_ACTIONS.valueOf(action);
		} catch (Exception e) {
			throw new RuntimeException("Hospital not updated because the action is unknown: " +action );
		}
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		final String newValue = (String) dbObject.get("value");
		System.out.println(newValue);
		
		if( action.equals(VALID_HOSPTIAL_ACTIONS.updateContract.name())){
			
			amiServices.updateHospitalContract(hospitalId, userName, newValue);
		}
		else if( action.equals(VALID_HOSPTIAL_ACTIONS.updateAccountSize.name())){
			
			amiServices.updateHospitalAccountSize(hospitalId, userName, newValue);
		}
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospital/updatehospital", method = RequestMethod.POST)
	@ResponseBody
	public void updateHospital(@RequestBody String data) throws IOException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		
		
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String action = (String) dbObject.get("action");
		
		try {
			VALID_HOSPTIAL_ACTIONS.valueOf(action);
		} catch (Exception e) {
			throw new RuntimeException("Hospital not updated because the action is unknown: " +action );
		}
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		if( action.equals(VALID_HOSPTIAL_ACTIONS.updatePhones.name())){
			
			final BasicDBList newValue = (BasicDBList) dbObject.get("value");
			List<Map<String, String>> updatedPhoneListOfMaps = objectMapper.readValue(newValue.toString(), List.class);
			List<Phone> updatedPhoneList = HospitalAttributeFactory.getPhonesFromMap(updatedPhoneListOfMaps);
			amiServices.updateHospitalPhones(hospitalId, userName, updatedPhoneList);
		}
		else if( action.equals(VALID_HOSPTIAL_ACTIONS.updateEmails.name())){
			
			final BasicDBList newValue = (BasicDBList) dbObject.get("value");
			List<Map<String, String>> updatedEmailListOfMaps = objectMapper.readValue(newValue.toString(), List.class);
			List<Email> updatedEmailList = HospitalAttributeFactory.getEmailsFromMap(updatedEmailListOfMaps);
			amiServices.updateHospitalEmails(hospitalId, userName, updatedEmailList);
		}
		
		else if( action.equals(VALID_HOSPTIAL_ACTIONS.updateAddresses.name())){
			final BasicDBList newValue = (BasicDBList) dbObject.get("value");
			List<Map<String, String>> updatedAddressesListOfMaps = objectMapper.readValue(newValue.toString(), List.class);
			List<Address> updatedEmailList = HospitalAttributeFactory.getAddressesFromMap(updatedAddressesListOfMaps);
			amiServices.updateHospitalAddresses(hospitalId, userName, updatedEmailList);
		}
		else if( action.equals(VALID_HOSPTIAL_ACTIONS.updateAcronym.name())){
			
			final String newValue = (String) dbObject.get("value");
			amiServices.updateHospitalAcronym(hospitalId, userName, newValue);
		}
		else if( action.equals(VALID_HOSPTIAL_ACTIONS.updateNotes.name())){
			
			final String newValue = (String) dbObject.get("value");
			amiServices.updateHospitalNotes(hospitalId, userName, newValue);
		}
	}
	

	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospital/updatemasteruser", method = RequestMethod.POST)
	@ResponseBody
	public void updateMasterUser(@RequestBody String data) throws IOException {
		

		DBObject dbObject = (DBObject)JSON.parse(data);
		final String newValue = (String) dbObject.get("value");
		final String hospitalId = (String) dbObject.get("hospitalId");
		final String action = (String) dbObject.get("action");
		
		try {
			 VALID_MASTER_USER_ACTIONS.valueOf(action);
		} catch (Exception e) {
			throw new RuntimeException("Master Uer not updated because the action is unknown: " +action );
		}
		
		HospitalView hospitalView = hospitalService.findHospital(hospitalId);
		final String userName     = hospitalView.getMasterUser().getUser();
		
		if( action.equals(VALID_MASTER_USER_ACTIONS.updatePwd.name())){
			
			amiServices.updateMasterUserPwd(hospitalId, userName, newValue);
			
		}else if (action.equals(VALID_MASTER_USER_ACTIONS.updateEmail.name())){
			
			amiServices.updateMasterUserEmail(hospitalId, userName, newValue);
		}
		 else if (action.equals(VALID_MASTER_USER_ACTIONS.updateFirstName.name())){
			 amiServices.updateMasterUserFirstName(hospitalId, userName, newValue);
		}
		else if (action.equals(VALID_MASTER_USER_ACTIONS.updateLastName.name())){
			
			amiServices.updateMasterUserLastName(hospitalId, userName, newValue);
		}else if (action.equals(VALID_MASTER_USER_ACTIONS.updateOccupation.name())){
			amiServices.updateMasterUserOccupation(hospitalId, userName, newValue);
		}
		else {
			throw new RuntimeException("Should never be here, but just in case. Master Uer not updated because the action is unknown: " +action );
		}
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/amiadminhome/hospital/switchmasteruser", method = RequestMethod.POST)
	@ResponseBody
	public String switchMasterUser(@RequestBody String data) throws IOException {
		
		DBObject dbObject = (DBObject)JSON.parse(data);
		final String newMasterUser = (String) dbObject.get("newMasterUser");
		final String hospitalId = (String) dbObject.get("hospitalId");
		
		amiServices.switchMasterUser(hospitalId, newMasterUser);
		AmiUserView updatedMasterUser = amiUserService.findAmiUser(newMasterUser);
		
		return objectMapper.writeValueAsString(updatedMasterUser);
	}
	
}

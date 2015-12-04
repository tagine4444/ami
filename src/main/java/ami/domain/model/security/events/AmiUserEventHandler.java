package ami.domain.model.security.events;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.hospitals.HospitalRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
@Service
public class AmiUserEventHandler {
	
	@Autowired
	private AmiUserRepository amiUserService;
	
	@Autowired
	private HospitalRepository hospitalService;

    
    @EventHandler
    public void handle(AmiUserCreatedEvent event) throws JsonProcessingException  {
    	amiUserService.createAmiUserView(event.getHospitalId(), event.getHospitalName(), event.getAmiUser());
    	hospitalService.addUser(event.getHospitalId(), event.getAmiUser());
    }
    
    @EventHandler
    public void handle(MasterUserPwdUpdatedEvent event) throws JsonProcessingException  {
    	amiUserService.upateMasterUserPwd(event.getUserName() , event.getNewPwd());
    	hospitalService.updateHospitalMasterUserPwd(event.getHospitalId() ,event.getUserName() , event.getNewPwd());
    }
    
    @EventHandler
    public void handle(MasterUserEmailUpdatedEvent event) throws JsonProcessingException  {
    	amiUserService.upateMasterUserEmail(event.getUserName() , event.getNewEmail());
    	hospitalService.updateHospitalMasterUserEmail(event.getHospitalId() ,event.getUserName() , event.getNewEmail());
    }
    
    @EventHandler
    public void handle(MasterUserFirstNameUpdatedEvent event) throws JsonProcessingException  {
    	amiUserService.upateMasterUserFirstName(event.getUserName() , event.getNewFirstName());
    	hospitalService.updateHospitalMasterFirstName(event.getHospitalId() ,event.getUserName() , event.getNewFirstName());
    }
    
    
}

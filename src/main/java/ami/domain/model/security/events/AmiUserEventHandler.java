package ami.domain.model.security.events;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.web.LoginController;

import com.fasterxml.jackson.core.JsonProcessingException;
@Service
public class AmiUserEventHandler {

	private static final Logger log = LoggerFactory.getLogger(AmiUserEventHandler.class);
	
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
    
    @EventHandler
    public void handle(MasterUserLastNameUpdatedEvent event) throws JsonProcessingException  {
    	amiUserService.upateMasterUserLastName(event.getUserName() , event.getNewLastName());
    	hospitalService.updateHospitalMasterLastName(event.getHospitalId() ,event.getUserName() , event.getNewLastName());
    }
    
    @EventHandler
    public void handle(MasterUserOccupationUpdatedEvent event) throws JsonProcessingException  {
    	amiUserService.upateMasterUserOccupation(event.getUserName() , event.getNewOccupation());
    	hospitalService.updateHospitalMasterOccupation(event.getHospitalId() ,event.getUserName() , event.getNewOccupation());
    }
    
    @EventHandler
    public void handle(MasterUserSwitchedEvent event) throws JsonProcessingException  {
    	amiUserService.switchMasterUserService(event.getHospitalId(), event.getNewMasterUser());
    	hospitalService.switchMasterUserService(event.getHospitalId(), event.getNewMasterUser());
    }
    @EventHandler
    public void handle(MasterUserSwitchedAbortedCauseAlreadyMasterUserEvent event) throws JsonProcessingException  {
    	final String user = SecurityContextHolder.getContext().getAuthentication().getName();
    	log.info("Master User Not Switched cause he's already a master user. Hospital:{} New MasterUser: {}. Operation done by AMI user: {} " , event.getHospitalId(), event.getNewMasterUser(), user);
    }
    
    
}

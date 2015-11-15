package ami.application.events.security;

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
    
    
}

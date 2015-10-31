package ami.application.events.security;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.application.services.security.AmiUserService;
import ami.application.services.security.HospitalService;

import com.fasterxml.jackson.core.JsonProcessingException;
@Service
public class AmiUserEventHandler {
	
	@Autowired
	private AmiUserService amiUserService;
	
	@Autowired
	private HospitalService hospitalService;

    
    @EventHandler
    public void handle(AmiUserCreatedEvent event) throws JsonProcessingException  {
    	amiUserService.createAmiUserView(event.getHospitalId(), event.getHospitalName(), event.getAmiUser());
    	hospitalService.addUser(event.getHospitalId(), event.getAmiUser());
    }
    
    
}

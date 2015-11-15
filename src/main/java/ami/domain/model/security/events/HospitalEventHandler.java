package ami.domain.model.security.events;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.HospitalRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class HospitalEventHandler {

	@Autowired
	private HospitalRepository hospitalViewService;

    
    @EventHandler
    public void handle(HospitalCreatedEvent event) throws JsonProcessingException  {
    	
    	Hospital hospital = event.getHospital();
    	DateTime hospitalActivationDate = event.getHospitalActivationDate();
    	hospitalViewService.createHospitalView(hospital, hospitalActivationDate);
    	
    }
}

package ami.domain.model.amicase.events;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.infrastructure.database.mongodb.amicase.AmendmentNotificationRepoMongo;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmendmentNotificationEventHandler {

	@Autowired
	private AmendmentNotificationRepoMongo repo;
	
	@Autowired
	private CommandGateway commandGateway;
	
	@EventHandler
    public void handle(AmendmentNotificationSentEvent event) throws JsonProcessingException {
    	repo.addAmendmentNotifications(event.getAmendmentNotification());
    	
    	boolean isAmendmentMadeByCustomer = event.getAmendmentNotification().getAmendment().isCustomerAmendment();
    	
    	if(!isAmendmentMadeByCustomer){
    		// if here it means that the amendment has been made by the admin user i.e chuck. 
    		// so make prior amendment notification adminAware.
    		repo.superceedPriorUnAwareAdminAmendmentNotification(event.getAmendmentNotification());
			
		}
    	
    }
}
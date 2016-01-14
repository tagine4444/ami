package ami.infrastructure.database.mongodb.amicase;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.AmendmentNotification;
import ami.domain.model.amicase.amirequest.repo.AmendmentNotificationRepository;

@Service
public class AmendmentNotificationRepoMongo implements AmendmentNotificationRepository  {

	
	public final static String AMENDMENT_NOTIFICATION_VIEW = "viewAmendmentNotification";

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private MongoTemplate mongo;
	
	@Override
	public void addAmendmentNotifications(AmendmentNotification amendmentNotification) {
		
		mongo.save(amendmentNotification, AMENDMENT_NOTIFICATION_VIEW);
	}
	
	@Override
	public List<AmendmentNotification> getAmendmentNotificationsForAdmin() {
		
		List<AmendmentNotification>  notifications = mongo.find(Query.query(Criteria.where("adminAware").is(false)), 
				AmendmentNotification.class,AMENDMENT_NOTIFICATION_VIEW);
		
		return notifications;
		
	}

	public void superceedPriorUnAwareAdminAmendmentNotification(
			AmendmentNotification amendmentNotification) {
		
		 Query query = Query.query( Criteria.where("caseNumber").is(amendmentNotification.getCaseNumber())
			      .andOperator(Criteria.where("amendmentId").lt(amendmentNotification.getAmendmentId()),
			    		        Criteria.where("adminAware").is(Boolean.FALSE))
			   );
		 
		Update update = new Update();
		update.set("adminAware", Boolean.TRUE);
		
		mongo.updateMulti(
				query,
	            update,
	            AMENDMENT_NOTIFICATION_VIEW);
		
	}
	
	
}

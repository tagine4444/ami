package ami.application.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ami.domain.model.amicase.AmendmentNotification;
import ami.infrastructure.database.mongodb.amicase.AmendmentNotificationRepoMongo;

@Service
public class AmendmentNotificationServiceImpl implements AmendmentNotificationService {

	@Autowired
	private AmendmentNotificationRepoMongo notificationRepo;
	

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public String getAmendmentNotificationsForAdmin() throws JsonProcessingException {
	
		List<AmendmentNotification> notifi = notificationRepo.getAmendmentNotificationsForAdmin();
		String anotificationString = objectMapper.writeValueAsString(notifi);
		return anotificationString;
	}

}

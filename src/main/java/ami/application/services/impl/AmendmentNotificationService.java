package ami.application.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface AmendmentNotificationService {

	String getAmendmentNotificationsForAdmin() throws JsonProcessingException;
}

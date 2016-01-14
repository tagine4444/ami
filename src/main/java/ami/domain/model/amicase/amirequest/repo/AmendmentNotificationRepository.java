package ami.domain.model.amicase.amirequest.repo;

import java.util.List;

import ami.domain.model.amicase.AmendmentNotification;

public interface AmendmentNotificationRepository {

	void addAmendmentNotifications(AmendmentNotification amendmentNotification);

	List<AmendmentNotification> getAmendmentNotificationsForAdmin();
}

package ami.domain.model.amicase.events;

import ami.domain.model.amicase.AmendmentNotification;

public class AmendmentNotificationSentEvent {

	private final String id;
	private final AmendmentNotification amendmentNotification;
    
    
    public AmendmentNotificationSentEvent( AmendmentNotification amendmentNotification) {
    	this.id = amendmentNotification.getCaseNumber();
    	this.amendmentNotification =amendmentNotification;
	}


	public String getId() {
		return id;
	}

	public AmendmentNotification getAmendmentNotification() {
		return amendmentNotification;
	}
}

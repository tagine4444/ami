package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.amicase.AmendmentNotification;

public class SendAmendNotificationCmd {

	@TargetAggregateIdentifier
    private final String id;
	private final AmendmentNotification amendmentNotification;
    
    
    public SendAmendNotificationCmd( AmendmentNotification amendmentNotification) {
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

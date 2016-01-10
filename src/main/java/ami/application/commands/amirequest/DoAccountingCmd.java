package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class DoAccountingCmd {
	
	@TargetAggregateIdentifier
    private final String id;
    private final String userName;
    private DateTime dateTime;
	
    public DoAccountingCmd(String id, String userName, DateTime dateTime) {
    	
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
    }

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

}

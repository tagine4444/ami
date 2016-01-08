package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class UpdateRadiographicImpressionCmd {

	@TargetAggregateIdentifier
    private final String id;
    private final String userName;
    private final DateTime dateTime;
    private final String radiographicImpression;
	
	public UpdateRadiographicImpressionCmd(String caseNumber,
			String userName, DateTime dateTime,
			String radiographicImpression) {
		
		this.id = caseNumber;
		this.userName = userName;
		this.radiographicImpression =radiographicImpression;
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


	public String getRadiographicImpression() {
		return radiographicImpression;
	}

	
}

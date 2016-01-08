package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class UpdateRadiographicInterpretationCmd {

	@TargetAggregateIdentifier
    private final String id;
    private final String userName;
    private final DateTime dateTime;
    private final String radiographicInterpretation;
	
	public UpdateRadiographicInterpretationCmd(String caseNumber,
			String userName, DateTime dateTime,
			String radiographicInterpretation) {
		
		this.id = caseNumber;
		this.userName = userName;
		this.radiographicInterpretation =radiographicInterpretation;
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

	public String getRadiographicInterpretation() {
		return radiographicInterpretation;
	}

}

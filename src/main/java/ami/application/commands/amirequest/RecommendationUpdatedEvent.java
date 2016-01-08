package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class RecommendationUpdatedEvent {

	@TargetAggregateIdentifier
    private final String id;
    private final String userName;
    private final DateTime dateTime;
    private final String recommendation;
	
	public RecommendationUpdatedEvent(String caseNumber,
			String userName, DateTime dateTime,
			String recommendation) {
		
		this.id = caseNumber;
		this.userName = userName;
		this.recommendation =recommendation;
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

	public String getRecommendation() {
		return recommendation;
	}

}

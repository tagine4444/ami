package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class CloseCaseCmd {
	
	@TargetAggregateIdentifier
    private final String id;
    private final String userName;
    private final DateTime dateTime;
    private final String radiographicInterpretation;
	private String radiographicImpression;
	private String recommendation;
	
    public CloseCaseCmd(String id, String userName, DateTime dateTime,
    		String radiographicInterpretation, 
			String radiographicImpression,String recommendation) {
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
        this.radiographicImpression = radiographicImpression;
        this.radiographicInterpretation = radiographicInterpretation;
        this.recommendation = recommendation;
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

	public String getRadiographicImpression() {
		return radiographicImpression;
	}

	public String getRecommendation() {
		return recommendation;
	}

}

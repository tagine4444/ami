package ami.domain.model.amicase.events;

import org.joda.time.DateTime;

public class RadiographicImpressionUpdatedEvent {

    private final String id;
    private final String userName;
    private final DateTime dateTime;
    private final String radiographicImpression;
	
	public RadiographicImpressionUpdatedEvent(String caseNumber,
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

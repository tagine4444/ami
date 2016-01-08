package ami.domain.model.amicase.events;

import org.joda.time.DateTime;

public class CaseClosedEvent {
	
    private final String id;
    private final String userName;
    private final DateTime dateTime;
//    private final String radiographicInterpretation;
//    private final String radiographicImpression;
//    private final String recommendation;
	
    public CaseClosedEvent(String id, String userName, DateTime dateTime) {
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
//        this.radiographicImpression = radiographicImpression;
//        this.radiographicInterpretation = radiographicInterpretation;
//        this.recommendation = recommendation;
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

//	public String getRadiographicInterpretation() {
//		return radiographicInterpretation;
//	}
//
//	public String getRadiographicImpression() {
//		return radiographicImpression;
//	}
//
//	public String getRecommendation() {
//		return recommendation;
//	}

}

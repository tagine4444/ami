package ami.domain.model.amicase.events;

import org.joda.time.DateTime;

public class CaseIsAlreadyInProgressEvent {

	 private final String id;
	    private final String userName;
	    private final DateTime dateTime;
		
	    public CaseIsAlreadyInProgressEvent(String id, String userName, DateTime dateTime) {
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

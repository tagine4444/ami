package ami.domain.model.amicase.events;

import org.joda.time.DateTime;

public class DraftCaseDeletedEvent {

	  	private final String id;
		private final String userName;
		private final String hospitalId;
		private final DateTime dateTime;
		

	    public DraftCaseDeletedEvent(String id,  String userName,
				    String hospitalId,
				    DateTime dateTime) {
	    	
	        this.id = id;
	        this.userName = userName;
	        this.hospitalId = hospitalId;
			this.dateTime = dateTime;
	    }

		public String getId() {
			return id;
		}


		public String getUserName() {
			return userName;
		}


		public String getHospitalId() {
			return hospitalId;
		}


		public DateTime getDateTime() {
			return dateTime;
		}
	    
}

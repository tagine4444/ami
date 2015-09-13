package ami.application.events.amirequest;

import org.joda.time.DateTime;


public class UploadFileRequestedEvent {

	 	private final String id;
	    private final String userName;
	    private final String fileName;
	    private final String filePath;
	    private final DateTime creationDate;
	    
	    public UploadFileRequestedEvent(String id,  String userName,   
	    		 String fileName, String filePath, DateTime creationDate) {
	        this.id = id;
	        this.userName = userName;
	        this.fileName = fileName;
	        this.filePath = filePath;
	        this.creationDate = creationDate;
	    }

		public String getId() {
			return id;
		}

		public String getUserName() {
			return userName;
		}


		public String getFileName() {
			return fileName;
		}

		public String getFilePath() {
			return filePath;
		}

		public DateTime getCreationDate() {
			return creationDate;
		}
	    
}

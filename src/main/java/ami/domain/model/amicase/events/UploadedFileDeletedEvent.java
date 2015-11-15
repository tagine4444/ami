package ami.domain.model.amicase.events;

import org.joda.time.DateTime;

public class UploadedFileDeletedEvent {
 //comment
    private final String id;
    private final String userName;
    private final String fileName;
    private DateTime dateTime;
	
    public UploadedFileDeletedEvent(String id,  String userName,   
    		String fileName, DateTime dateTime) {
        this.id = id;
        this.userName = userName;
        this.fileName = fileName;
        this.dateTime = dateTime;
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

	public DateTime getDateTime() {
		return dateTime;
	}
}

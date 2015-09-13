package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

public class UploadFileCommand {

	@TargetAggregateIdentifier
    private final String id;
    private final String userName;

    private final String fileName;
    private final String filePath;
    private DateTime creationDate;
    
    public UploadFileCommand(String id,  String userName,   
    		String fileName, String filePath,DateTime creationDate) {
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

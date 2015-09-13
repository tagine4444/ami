package ami.domain.amirequest;

import org.joda.time.DateTime;

public class FileUploadInfo {
	
	private String requestNumber;
	private DateTime creationDate;
	private String fileName;
	private String filePath;
	private String userName;
	
	public FileUploadInfo(
						String requestNumber,
						  String fileName,
						  String filePath,
						  String userName,
						  DateTime creationDate) {
		this.requestNumber = requestNumber;
		this.fileName = fileName;
		this.filePath = filePath;
	    this.userName = userName;
	    this.creationDate = creationDate;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getUserName() {
		return userName;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

}

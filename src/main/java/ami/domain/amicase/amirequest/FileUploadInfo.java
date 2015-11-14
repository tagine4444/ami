package ami.domain.amicase.amirequest;

import org.joda.time.DateTime;

public class FileUploadInfo {
	
	private String requestNumber;
	private DateTime creationDate;
	private String fileName;
	private String filePath;
	private String originalFileName;
	private String userName;
	
	public FileUploadInfo(
						String requestNumber,
						  String fileName,
						  String originalFileName,
						  String filePath,
						  String userName,
						  DateTime creationDate) {
		this.requestNumber = requestNumber;
		this.fileName = fileName;
		this.originalFileName = originalFileName;
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

	public String getOriginalFileName() {
		return originalFileName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result
				+ ((requestNumber == null) ? 0 : requestNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileUploadInfo other = (FileUploadInfo) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (requestNumber == null) {
			if (other.requestNumber != null)
				return false;
		} else if (!requestNumber.equals(other.requestNumber))
			return false;
		return true;
	}
	
	

}

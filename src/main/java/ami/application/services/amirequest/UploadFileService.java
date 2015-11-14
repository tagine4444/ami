package ami.application.services.amirequest;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import ami.domain.amicase.amirequest.FileUploadInfo;


public interface UploadFileService {

	void upload(String requestNumber,
			String userName,
			String flowChunkNumber, String flowChunkSize,
			String flowFilename, String flowRelativePath,
			String flowTotalChunks, String flowIdentifier,
			String flowTotalSize, MultipartFile file) throws ServletException,
			IOException;

	List<FileUploadInfo> deleteUploadedFile(String fileName, String requestNumber,
			String userName, DateTime dateTime);

	 
}

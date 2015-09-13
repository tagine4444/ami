package ami.application.services.amirequest;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.web.multipart.MultipartFile;


public interface UploadFileService {

	void upload(String requestNumber,
			String userName,
			String flowChunkNumber, String flowChunkSize,
			String flowFilename, String flowRelativePath,
			String flowTotalChunks, String flowIdentifier,
			String flowTotalSize, MultipartFile file) throws ServletException,
			IOException;

	 
}

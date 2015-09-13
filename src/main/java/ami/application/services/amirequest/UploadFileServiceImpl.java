package ami.application.services.amirequest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ami.application.commands.amirequest.UploadFileCommand;
import ami.controller.HttpUtils;
import ami.controller.ResumableInfo;
import ami.controller.ResumableInfoStorage;

@Service
public class UploadFileServiceImpl implements UploadFileService{
	
	public static final String UPLOAD_DIR = "uploads";
	
	@Autowired
	private CommandGateway commandGateway;
	
	
	@Override
	public void upload(
			    String requestNumber,
			    String userName,
			    String flowChunkNumber, 
				String flowChunkSize, 
				String flowFilename, 
				String flowRelativePath, 
				String flowTotalChunks,
				String flowIdentifier,
				String flowTotalSize,
			 
			 MultipartFile file) throws ServletException, IOException {
	        int resumableChunkNumber        =  HttpUtils.toInt(flowChunkNumber, -1);

	        ResumableInfo info = getResumableInfo(requestNumber,flowChunkNumber,flowChunkSize, 
	        		flowFilename, flowRelativePath, flowTotalSize,flowIdentifier,flowTotalChunks );
	        
	        RandomAccessFile raf = new RandomAccessFile(info.resumableFilePath, "rw");

	        //Seek to position
	        raf.seek((resumableChunkNumber - 1) * info.resumableChunkSize);

	        //Save to file
	        InputStream is = new ByteArrayInputStream(file.getBytes());
          //InputStream is = request.getInputStream();
	        long readed = 0;
	        //long content_length = request.getContentLength();
	        long content_length = Long.valueOf(flowChunkSize);
	        byte[] bytes = new byte[1024 * 100];
	        while(readed < content_length) {
	            int r = is.read(bytes);
	            if (r < 0)  {
	                break;
	            }
	            raf.write(bytes, 0, r);
	            readed += r;
	        }
	        raf.close();


	        //Mark as uploaded.
	        info.uploadedChunks.add(new ResumableInfo.ResumableChunkNumber(resumableChunkNumber));
	        
	        if (info.checkIfUploadFinished()) { //Check if all chunks uploaded, and change filename
	            ResumableInfoStorage.getInstance().remove(info);
	           // response.getWriter().print("All finished.");
	            
	            commandGateway.sendAndWait(new UploadFileCommand(requestNumber, userName, info.resumableFilename, info.resumableFilePath, new DateTime()));
	        } else {
	            //response.getWriter().print("Upload");
	        }
	 
	
	}
	
	 private ResumableInfo getResumableInfo(
			    String requestNumber,
	    		String flowChunkNumber, 
				String flowChunkSize, 
				String flowFilename, 
				String flowRelativePath, 
				String flowTotalSize,
				String flowIdentifier,
				String flowTotalChunks
	    		) throws ServletException {
	        String base_dir = UPLOAD_DIR  ;

	        
//	        String flowChunkNumber, 
//			String flowChunkSize, 
//			String flowFilename, 
//			String flowRelativePath, 
//			String flowTotalChunks
	        
	        int resumableChunkSize          = HttpUtils.toInt(flowChunkSize, -1);
	        long resumableTotalSize         = HttpUtils.toLong(flowTotalSize  , -1) ;
	        String resumableIdentifier      = flowIdentifier;
	        String resumableFilename        = requestNumber+"_"+flowFilename;
	        String resumableRelativePath    = flowRelativePath;
	        
	        
	        //Here we add a ".temp" to every upload file to indicate NON-FINISHED
	        String resumableFilePath        = new File(base_dir, resumableFilename).getAbsolutePath() + ".temp";

	        ResumableInfoStorage storage = ResumableInfoStorage.getInstance();

	        ResumableInfo info = storage.get(resumableChunkSize, resumableTotalSize,
	                resumableIdentifier, resumableFilename, resumableRelativePath, 
	                resumableFilePath, flowTotalChunks );
	        if (!info.vaild())         {
	            storage.remove(info);
	            throw new ServletException("Invalid request params.");
	        }
	        return info;
	    }
}
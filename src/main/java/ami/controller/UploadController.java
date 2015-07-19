package ami.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	public static final String UPLOAD_DIR = "uploads";
	
	
	@RequestMapping(value = "/ami/doupload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="flowChunkNumber") String flowChunkNumber, 
			@RequestParam(value="flowChunkSize") String flowChunkSize, 
			@RequestParam(value="flowFilename") String flowFilename, 
			@RequestParam(value="flowRelativePath") String flowRelativePath, 
			@RequestParam(value="flowTotalChunks") String flowTotalChunks, 
			@RequestParam(value="flowIdentifier") String flowIdentifier, 
			@RequestParam(value="flowTotalSize") String flowTotalSize, 
			@RequestParam("file") MultipartFile file,
			Model model
			
			) 
	{
		
		log.info(  "flowIdentifier: "+flowIdentifier +"flowFilename: "+flowFilename+ " flowTotalSize: " +flowTotalSize+ " flowChunkNumber: "+flowChunkNumber +": flowChunkSize:"+ flowChunkSize
				+ " flowRelativePath: "+ flowRelativePath+ " flowTotalChunks: "+flowTotalChunks);
		
		try 
		{
			doPost( flowChunkNumber, flowChunkSize, flowFilename, 
					flowRelativePath, flowTotalChunks,flowIdentifier,
					flowTotalSize,
//					request, 
					file);
		} 
		catch (Exception e) 
		{
			log.error("Error Uploading file: flowFilename:"+ flowFilename 
					+" flowChunkNumber: "+flowChunkNumber + " flowIdentifier: " + flowIdentifier
					+" flowTotalChunks: "+flowTotalChunks +" flowChunkSize: "+flowChunkSize,e);
			//TODO add redirect to error page or error message
		}
		
		return "{}";
	}
	
	@RequestMapping(value = "/ami/upload", method = RequestMethod.GET)
	@ResponseBody
	public String upload(@RequestParam(value="flowChunkNumber") String flowChunkNumber, 
			@RequestParam(value="flowChunkSize") String flowChunkSize, 
			@RequestParam(value="flowFilename") String flowFilename, 
			@RequestParam(value="flowRelativePath") String flowRelativePath, 
			@RequestParam(value="flowTotalChunks") String flowTotalChunks, 
			@RequestParam(value="flowIdentifier") String flowIdentifier, 
//			HttpServletRequest request ,
			@RequestParam("file") MultipartFile file) throws IOException {
		
		
		System.out.println("calling get!!!!!!!!!!!!!!!!!!!!!!");
		return "{}";
	}
	
	
	protected void doPost(
			 String flowChunkNumber, 
				String flowChunkSize, 
				String flowFilename, 
				String flowRelativePath, 
				String flowTotalChunks,
				String flowIdentifier,
				String flowTotalSize,
			 
			 MultipartFile file) throws ServletException, IOException {
	        int resumableChunkNumber        =  HttpUtils.toInt(flowChunkNumber, -1);

	        ResumableInfo info = getResumableInfo(flowChunkNumber,flowChunkSize, 
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
	        } else {
	            //response.getWriter().print("Upload");
	        }
	    }
	 

	    private ResumableInfo getResumableInfo(
	    		String flowChunkNumber, 
				String flowChunkSize, 
				String flowFilename, 
				String flowRelativePath, 
				String flowTotalSize,
				String flowIdentifier,
				String flowTotalChunks
	    		) throws ServletException {
	        String base_dir = UPLOAD_DIR;

	        
//	        String flowChunkNumber, 
//			String flowChunkSize, 
//			String flowFilename, 
//			String flowRelativePath, 
//			String flowTotalChunks
	        
	        int resumableChunkSize          = HttpUtils.toInt(flowChunkSize, -1);
	        long resumableTotalSize         = HttpUtils.toLong(flowTotalSize  , -1) ;
	        String resumableIdentifier      = flowIdentifier;
	        String resumableFilename        = flowFilename;
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

package ami.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ami.application.services.amirequest.UploadFileService;

@Controller
public class UploadController {

	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@RequestMapping(value = "/ami/doupload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam(value="flowChunkNumber") String flowChunkNumber, 
			@RequestParam(value="flowChunkSize") String flowChunkSize, 
			@RequestParam(value="flowFilename") String flowFilename, 
			@RequestParam(value="flowRelativePath") String flowRelativePath, 
			@RequestParam(value="flowTotalChunks") String flowTotalChunks, 
			@RequestParam(value="flowIdentifier") String flowIdentifier, 
			@RequestParam(value="flowTotalSize") String flowTotalSize, 
			@RequestParam(value="requestNumber") String requestNumber, 
			@RequestParam("file") MultipartFile file,
			Model model
			
			) 
	{
		
		final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info(  "flowIdentifier: "+flowIdentifier +"flowFilename: "+flowFilename+ " flowTotalSize: " +flowTotalSize+ " flowChunkNumber: "+flowChunkNumber +": flowChunkSize:"+ flowChunkSize
				+ " flowRelativePath: "+ flowRelativePath+ " flowTotalChunks: "+flowTotalChunks 
				+ " requestNumber " +requestNumber + " userName " + userName);
		
		try 
		{
			uploadFileService.upload(requestNumber,userName, flowChunkNumber, flowChunkSize, flowFilename, 
					flowRelativePath, flowTotalChunks,flowIdentifier,
					flowTotalSize,
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
	
	
}

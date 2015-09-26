package ami.application.events.amirequest;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.application.services.amirequest.AmiRequestService;
import ami.domain.amirequest.AmiRequest;
import ami.domain.amirequest.FileUploadInfo;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmiRequestEventHandler {

	@Autowired
	private AmiRequestService amiServiceRequestSvc;

    @EventHandler
    public void handle(NewAmiRequestSubmittedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
       
    	amiServiceRequestSvc.createAmiRequestView(event.getAmiRequestJson(), event.getUserName(), 
    			event.getHospitalName(), event.getHospitalId(), 
    			event.getHasBeenSavedAndSubmittedToRadiologist(), null,
    			null,null,
    			event.isEditable(),
    			time);
    	
//    	System.out.println(String.format("We've got an AMI Request which id is: %s (created at %s)",
//                                         event.getId(),
//                                         time.toString("d-M-y H:m")));
//        
//        System.out.println("the ami request is ");
//        System.out.println("==================================\n\n");
//        System.out.println(event.getAmiRequestJson());
    }
    
    @EventHandler
    public void handle(AmiRequestSavedAsDraftEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestSvc.createAmiRequestView(event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			null, null,null,null,event.isEditable(),
    			time);
    	
    	
    }
    
    @EventHandler
    public void handle(AmiRequestUpdatedAsDraftEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestSvc.updateAmiRequestView(event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			null,null,null,null, 
    			event.isEditable(),
    			time);
    }
    
    @EventHandler
    public void handle(DraftAmiRequestSubmittedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestSvc.updateAmiRequestView(event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			event.getHasBeenSavedAndSubmittedToRadiologist(),null,null,null, 
    			event.isEditable(),
    			time);
    }
    
    @EventHandler
    public void handle(UploadFileRequestedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	FileUploadInfo info = new FileUploadInfo(event.getId(),event.getFileName(), event.getOriginalFileName(), event.getFilePath(), event.getUserName(),time);
    	  
    	amiServiceRequestSvc.updateUploadedFileList(info, time);
		
    }
    
    @EventHandler
    public void handle(UploadedFileDeletedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestSvc.deleteUploadedFile(event.getFileName() ,event.getId(), time);
    	
    }
    
    
}

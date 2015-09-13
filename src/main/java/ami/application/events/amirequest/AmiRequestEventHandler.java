package ami.application.events.amirequest;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.application.services.amirequest.AmiRequestService;
import ami.application.views.AmiRequestView;
import ami.domain.amirequest.FileUploadInfo;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmiRequestEventHandler {

	@Autowired
	private AmiRequestService amiServiceRequestSvc;

    @EventHandler
    public void handle(AmiRequestCreatedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
       
//    	boolean hasBeenSavedAndSubmittedToRadiologist, 
//		boolean interpretationInProgress,              
//		boolean interpretationReadyForReview,          
//		boolean interpretationReadyComplete,           
//		boolean editable, 
    	amiServiceRequestSvc.createAmiRequestView(event.getAmiRequestJson(), event.getUserName(), 
    			event.getHospitalName(), event.getHospitalId(), 
    			event.getHasBeenSavedAndSubmittedToRadiologist(), event.getInterpretationInProgress(),
    			event.getInterpretationReadyForReview(),event.getInterpretationReadyComplete(),
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
    			event.getHasBeenSavedAndSubmittedToRadiologist(), event.getInterpretationInProgress(),
    			event.getInterpretationReadyForReview(),event.getInterpretationReadyComplete(),
    			event.isEditable(),
    			time);
    	
    	
    }
    
    @EventHandler
    public void handle(AmiRequestUpdatedAsDraftEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestSvc.updateAmiRequestView(event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			event.getHasBeenSavedAndSubmittedToRadiologist(), event.getInterpretationInProgress(),
    			event.getInterpretationReadyForReview(),event.getInterpretationReadyComplete(),
    			event.isEditable(),
    			time);
    	
    	
    }
    
    @EventHandler
    public void handle(UploadFileRequestedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	FileUploadInfo info = new FileUploadInfo(event.getId(),event.getFileName(), event.getFilePath(), event.getUserName(),time);
    	  
    	amiServiceRequestSvc.updateUploadedFileList(info, time);
		
    }
    
    
}

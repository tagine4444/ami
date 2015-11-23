package ami.domain.model.amicase.events;

import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.amirequest.repo.AmiRequestRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmiRequestEventHandler {

	@Autowired
	private AmiRequestRepository amiServiceRequestRepo;

    @EventHandler
    public void handle(NewAmiRequestSubmittedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
       
    	amiServiceRequestRepo.createAmiRequestView(event.getId(), event.getAmiRequestJson(), event.getUserName(), 
    			event.getHospitalName(), event.getHospitalId(), 
    			event.getHasBeenSavedAndSubmittedToRadiologist(), null,
    			null,null,
//    			event.isEditable(),
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
    	
    	amiServiceRequestRepo.createAmiRequestView(event.getId(), event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			null, null,null,null,
    			time);
    	
    	
    }
    
    @EventHandler
    public void handle(AmiRequestUpdatedAsDraftEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestRepo.updateAmiRequestView(event.getId(), event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			null,null,null,null, 
    			true,
    			time);
    }
    
    @EventHandler
    public void handle(DraftAmiRequestSubmittedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestRepo.updateAmiRequestView(event.getId(), event.getAmiRequestJson(), event.getUserName(),
    			event.getHospitalName(), event.getHospitalId(),
    			event.getHasBeenSavedAndSubmittedToRadiologist(),null,null,null, 
    			true,
    			time);
    }
    
    @EventHandler
    public void handle(UploadFileRequestedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	FileUploadInfo info = new FileUploadInfo(event.getId(),event.getFileName(), event.getOriginalFileName(), event.getFilePath(), event.getUserName(),time);
    	  
    	amiServiceRequestRepo.updateUploadedFileList(info, time);
		
    }
    
    @EventHandler
    public void handle(UploadedFileDeletedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
    	
    	amiServiceRequestRepo.deleteUploadedFile(event.getFileName() ,event.getId(), time);
    	
    }
    
    
}
package ami.domain.model.amicase.events;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.SendAmendNotificationCmd;
import ami.domain.model.amicase.AmendmentNotification;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.amirequest.repo.AmiRequestRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class AmiRequestEventHandler {

	@Autowired
	private AmiRequestRepository amiServiceRequestRepo;
	
	@Autowired
	private CommandGateway commandGateway;
	
	
    @EventHandler
    public void handle(NewAmiRequestSubmittedEvent event, @Timestamp DateTime time) throws JsonProcessingException {
       
    	amiServiceRequestRepo.createAmiRequestView(event.getId(), event.getAmiRequestJson(), event.getUserName(), 
    			event.getHospitalName(), event.getHospitalId(), 
    			event.getHasBeenSavedAndSubmittedToRadiologist(), null,
    			null,null,
    			time, event.getContract(), event.getAccountSize(),null);
    	
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
    			time, event.getContract(), event.getAccountSize(),null);
    	
    	
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
    
    @EventHandler
    public void handle(CaseSwitchedToInProgressEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.switchCaseToInProgress(event.getDateTime() ,event.getId(), event.getUserName());
    }
    
    @EventHandler
    public void handle(CaseSwitchedToReadyForReviewEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.switchCaseToReadyForReview(event.getDateTime() ,event.getId(), event.getUserName());
    }
    @EventHandler
    public void handle(CaseClosedEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.closeCase(event.getDateTime() ,event.getId(), event.getUserName());
    }
    
    
    @EventHandler
    public void handle(RadiographicInterpretationUpdatedEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.updateRadiographicInterpretation(event.getDateTime() ,event.getId(), event.getUserName(),
    			event.getRadiographicInterpretation());
    }
    
    @EventHandler
    public void handle(RadiographicImpressionUpdatedEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.updateRadiographicImpression(event.getDateTime() ,event.getId(), event.getUserName(),
    			 event.getRadiographicImpression());
    }
    @EventHandler
    public void handle(RecommendationUpdatedEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.updateRecommendation(event.getDateTime() ,event.getId(), event.getUserName(),
    			 event.getRecommendation());
    }
    
    @EventHandler
    public void handle(CaseAmendedEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.amendCase(event.getId(), event.getAmendment());
    	
    	AmendmentNotification notification = new  AmendmentNotification(event.getId(), event.getAmendment());
    	commandGateway.sendAndWait(new SendAmendNotificationCmd(notification));
    }
    
    
    @EventHandler
    public void handle(AccountingDoneEvent event) throws JsonProcessingException {
    	amiServiceRequestRepo.updateAccountingDone(event.getId(), event.getDateTime(), event.getUserName());
    }
    
}

package ami.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.DateTime;

import ami.application.commands.amirequest.DeleteUploadedFileCommand;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.SubmitDraftAmiRequestCmd;
import ami.application.commands.amirequest.SubmitNewAmiRequestCmd;
import ami.application.commands.amirequest.UpdateAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.UploadFileCommand;
import ami.application.events.amirequest.AmiRequestSavedAsDraftEvent;
import ami.application.events.amirequest.AmiRequestUpdatedAsDraftEvent;
import ami.application.events.amirequest.DraftAmiRequestSubmittedEvent;
import ami.application.events.amirequest.NewAmiRequestSubmittedEvent;
import ami.application.events.amirequest.UploadFileRequestedEvent;
import ami.application.events.amirequest.UploadedFileDeletedEvent;
import ami.domain.amirequest.AmiRequest;
import ami.domain.amirequest.FileUploadInfo;

public class AmiRequestAggregate extends AbstractAnnotatedAggregateRoot {

	@AggregateIdentifier
	private String id;
	
	//private String amiRequest;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	private List<FileUploadInfo> fileUploads = new ArrayList<FileUploadInfo>();
	
//	private boolean editable;
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime interpretationReadyComplete;
	
	// No-arg constructor, required by Axon
	public AmiRequestAggregate() {
	}

	
	// ----------------- Submit to radiologist (for the 1st time)  ----------------- 
	@CommandHandler
	public AmiRequestAggregate(SubmitNewAmiRequestCmd command) {
		apply(new NewAmiRequestSubmittedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), 
				command.getHospitalName(),
				command.getHospitalId(),
				command.getHasBeenSavedAndSubmittedToRadiologist()));
	}
	
	// ----------------- Save as Draft  (for the 1st time) ----------------- 
	@CommandHandler
	public AmiRequestAggregate(SaveAmiRequestAsDraftCmd command) {
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("Requests cannot be saved as draft save as draft once they have been submitted to the radiologist.");
		}
		apply(new AmiRequestSavedAsDraftEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName() ,
				 command.getHospitalId()
//				 ,command.isEditable()
				 ) );
	}
	
	
	// ===============================  Submit to Radio a Draft Req (update) =============================
	@CommandHandler
	public void submitDraftAmiRequestCmd(SubmitDraftAmiRequestCmd command) {
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("This request has already been submitted. It cannot be submitted to radiologist twice.");
		}
		apply(new DraftAmiRequestSubmittedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), 
				command.getHospitalName(),
				command.getHospitalId(),
				command.getHasBeenSavedAndSubmittedToRadiologist(),
				command.isEditable(), 
				command.getDateTime()));
	}
	
	// ===============================  update Draft Req (update) =============================
	@CommandHandler
    public void updateAmiRequestAsDraftCmd(UpdateAmiRequestAsDraftCmd command) {
		
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("This request has already been submitted. It cannot be updated.");
		}
		
		apply(new AmiRequestUpdatedAsDraftEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName() ,
				 command.getHospitalId()
//				 ,command.isEditable() 
				 , command.getDateTime()) );
    }
	
	@CommandHandler
    public void uploadFile(UploadFileCommand command) {

		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("This request has already been submitted. It can no longer upload files");
		}
        apply(new UploadFileRequestedEvent(command.getId(),
        		command.getUserName(),  command.getFileName(), command.getOriginalFileName() , command.getFilePath(), command.getCreationDate()));
    }
	
	@CommandHandler
	public void deleteUploadedFile(DeleteUploadedFileCommand command) {
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("This request has already been submitted. It can no longer del files");
		}
		apply(new UploadedFileDeletedEvent(command.getId(),
				command.getUserName(), command.getFileName(), command.getDateTime()));
	}


	// -==-=-=-=-=-=-=-=--=-=-=- EventSourceHandlers -==-=-=-=-=-=-=-=--=-=-=- 
	
	@EventSourcingHandler
	public void on(NewAmiRequestSubmittedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.hasBeenSavedAndSubmittedToRadiologist = event.getHasBeenSavedAndSubmittedToRadiologist();
		//this.editable   = event.isEditable();    
	}
	
	@EventSourcingHandler
	public void on(SubmitDraftAmiRequestCmd event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
//		this.editable   = event.isEditable();    
	}
	
	
	@EventSourcingHandler
	public void on(AmiRequestSavedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
//		this.editable   = event.isEditable(); 
	}
	
	@EventSourcingHandler
	public void on(AmiRequestUpdatedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
//		this.editable   = event.isEditable(); 
		
	}
	
	@EventSourcingHandler
	public void on(DraftAmiRequestSubmittedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
//		this.editable   = event.isEditable(); 
	}
	
	
	@EventSourcingHandler
	public void on(UploadFileRequestedEvent event) {
		
		
		FileUploadInfo info = 
				new FileUploadInfo(event.getId(), event.getFileName(),event.getOriginalFileName() , event.getFilePath(), userName, 
						event.getCreationDate());
		this.fileUploads.add(info);
		
	}
	@EventSourcingHandler
	public void on(UploadedFileDeletedEvent event) {
		
		if(this.fileUploads==null || !this.fileUploads.isEmpty())
		{
			for (Iterator<FileUploadInfo> iterator = fileUploads.iterator(); iterator.hasNext();) {
				
				FileUploadInfo fileUploadInfo = (FileUploadInfo) iterator.next();
				
				if(fileUploadInfo.getFileName().equals(event.getFileName())){
					iterator.remove();
				}
			}
		}
		
	}
	
	private boolean hasBeenSavedAndSubmittedToRadiologist() {
		if ( hasBeenSavedAndSubmittedToRadiologist!=null ){
			return true;
		}
		return false;
	}
}

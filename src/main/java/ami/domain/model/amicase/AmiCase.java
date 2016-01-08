package ami.domain.model.amicase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.DateTime;

import ami.application.commands.amirequest.CloseCaseCmd;
import ami.application.commands.amirequest.DeleteUploadedFileCommand;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.SubmitDraftAmiRequestCmd;
import ami.application.commands.amirequest.SubmitNewAmiRequestCmd;
import ami.application.commands.amirequest.SwitchCaseToInProgressCmd;
import ami.application.commands.amirequest.SwitchCaseToReadyForReviewCmd;
import ami.application.commands.amirequest.UpdateAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.UpdateRadiographicImpressionCmd;
import ami.application.commands.amirequest.UpdateRadiographicInterpretationCmd;
import ami.application.commands.amirequest.UpdateRecommendationCmd;
import ami.application.commands.amirequest.UploadFileCommand;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.events.AmiRequestSavedAsDraftEvent;
import ami.domain.model.amicase.events.AmiRequestUpdatedAsDraftEvent;
import ami.domain.model.amicase.events.CaseClosedEvent;
import ami.domain.model.amicase.events.CaseIsAlreadyInProgressEvent;
import ami.domain.model.amicase.events.CaseSwitchedToInProgressEvent;
import ami.domain.model.amicase.events.CaseSwitchedToReadyForReviewEvent;
import ami.domain.model.amicase.events.DraftAmiRequestSubmittedEvent;
import ami.domain.model.amicase.events.NewAmiRequestSubmittedEvent;
import ami.domain.model.amicase.events.RadiographicImpressionUpdatedEvent;
import ami.domain.model.amicase.events.RadiographicInterpretationUpdatedEvent;
import ami.domain.model.amicase.events.RecommendationUpdatedEvent;
import ami.domain.model.amicase.events.UploadFileRequestedEvent;
import ami.domain.model.amicase.events.UploadedFileDeletedEvent;

public class AmiCase extends AbstractAnnotatedAggregateRoot {

	@AggregateIdentifier
	private String id;
	
	//private String amiRequest;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	private String contract;
	private String accountSize;
	private List<FileUploadInfo> fileUploads = new ArrayList<FileUploadInfo>();
	
//	private boolean editable;
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime caseClosed;
	
	private String radiographicInterpretation;
    private String radiographicImpression;
    private String recommendation;
	
	// No-arg constructor, required by Axon
	public AmiCase() {
	}

	
	// ----------------- Submit to radiologist (for the 1st time)  ----------------- 
	@CommandHandler
	public AmiCase(SubmitNewAmiRequestCmd command) {
		apply(new NewAmiRequestSubmittedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), 
				command.getHospitalName(),
				command.getHospitalId(),
				command.getHasBeenSavedAndSubmittedToRadiologist(), 
				command.getContract(),
				command.getAccountSize()));
	}
	
	// ----------------- Save as Draft  (for the 1st time) ----------------- 
	@CommandHandler
	public AmiCase(SaveAmiRequestAsDraftCmd command) {
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("Requests cannot be saved as draft save as draft once they have been submitted to the radiologist.");
		}
		apply(new AmiRequestSavedAsDraftEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName() ,
				 command.getHospitalId(),
				 command.getContract(), command.getAccountSize()
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
				command.getDateTime(),
				command.getContract(),
				command.getAccountSize()));
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
				 , command.getDateTime(),
				 command.getContract(),
					command.getAccountSize()) );
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
	
	
	@CommandHandler
	public void switchCaseToInProgress(SwitchCaseToInProgressCmd command) {
		if(interpretationInProgress!=null){
			apply(new CaseIsAlreadyInProgressEvent(command.getId(),
					command.getUserName(),  command.getDateTime()));
		}else{
			apply(new CaseSwitchedToInProgressEvent(command.getId(),
					command.getUserName(),  command.getDateTime()));
		}
		
	}
	
	@CommandHandler
	public void switchCaseToReadyForReview(SwitchCaseToReadyForReviewCmd command) {
		if(caseClosed!=null){
			throw new RuntimeException("Case "+this.id+" is closed. Can't be switched to ready for review");
		}else{
			apply(new CaseSwitchedToReadyForReviewEvent(command.getId(),
					command.getUserName(),  command.getDateTime(), command.getRadiographicInterpretation(), 
					command.getRadiographicImpression(), command.getRecommendation()));
		}
	}
	
	@CommandHandler
	public void closeCase(CloseCaseCmd command) {
		if(caseClosed!=null){
			throw new RuntimeException("Case "+this.id+" is already closed. Can't be closed again");
		}else{
			apply(new CaseClosedEvent(command.getId(),
					command.getUserName(),  command.getDateTime()
//					,command.getRadiographicInterpretation(), command.getRadiographicImpression(), command.getRecommendation()
					));
		}
		
	}
	
	@CommandHandler
	public void updateRadiographicInterpretation(UpdateRadiographicInterpretationCmd command) {
			apply(new RadiographicInterpretationUpdatedEvent(command.getId(),
					command.getUserName(),  command.getDateTime(),
					command.getRadiographicInterpretation()));
	}
	
	@CommandHandler
	public void updateRadiographicImpression(UpdateRadiographicImpressionCmd command) {
	
			apply(new RadiographicImpressionUpdatedEvent(command.getId(),
					command.getUserName(),  command.getDateTime(),
					command.getRadiographicImpression()));
		
	}
	
	@CommandHandler
	public void updateRecommendation(UpdateRecommendationCmd command) {
		
		apply(new RecommendationUpdatedEvent(command.getId(),
				command.getUserName(),  command.getDateTime(),
				command.getRecommendation()));
		
	}
	

	// -==-=-=-=-=-=-=-=--=-=-=- EventSourceHandlers -==-=-=-=-=-=-=-=--=-=-=- 
	
	@EventSourcingHandler
	public void on(NewAmiRequestSubmittedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.hasBeenSavedAndSubmittedToRadiologist = event.getHasBeenSavedAndSubmittedToRadiologist();
		this.contract = event.getContract();
		this.accountSize = event.getAccountSize();
	}
	
	@EventSourcingHandler
	public void on(SubmitDraftAmiRequestCmd event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.contract = event.getContract();
		this.accountSize = event.getAccountSize();
	}
	
	
	@EventSourcingHandler
	public void on(AmiRequestSavedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.contract = event.getContract();
		this.accountSize = event.getAccountSize();
	}
	
	@EventSourcingHandler
	public void on(AmiRequestUpdatedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.contract = event.getContract();
		this.accountSize = event.getAccountSize();
	}
	
	@EventSourcingHandler
	public void on(DraftAmiRequestSubmittedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.contract = event.getContract();
		this.accountSize = event.getAccountSize();
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
	
	@EventSourcingHandler
	public void on(CaseSwitchedToInProgressEvent event) {
		
		this.interpretationInProgress = event.getDateTime();
	}
	@EventSourcingHandler
	public void on(CaseIsAlreadyInProgressEvent event) {
		//nothing to do... 
	}
	
	@EventSourcingHandler
	public void on(CaseSwitchedToReadyForReviewEvent event) {
		this.interpretationReadyForReview = event.getDateTime();
		this.radiographicImpression = event.getRadiographicImpression();
		this.radiographicInterpretation = event.getRadiographicInterpretation();
		this.recommendation = event.getRecommendation();
	}
	
	@EventSourcingHandler
	public void on(CaseClosedEvent event) {
		this.caseClosed = event.getDateTime();
//		this.radiographicImpression = event.getRadiographicImpression();
//		this.radiographicInterpretation = event.getRadiographicInterpretation();
//		this.recommendation = event.getRecommendation();
	}
	
	@EventSourcingHandler
	public void on(RadiographicInterpretationUpdatedEvent event) {
		this.radiographicInterpretation = event.getRadiographicInterpretation();
	}
	@EventSourcingHandler
	public void on(RadiographicImpressionUpdatedEvent event) {
		this.radiographicImpression = event.getRadiographicImpression();
	}
	@EventSourcingHandler
	public void on(RecommendationUpdatedEvent event) {
		this.recommendation = event.getRecommendation();
	}
	
	private boolean hasBeenSavedAndSubmittedToRadiologist() {
		if ( hasBeenSavedAndSubmittedToRadiologist!=null ){
			return true;
		}
		return false;
	}
}

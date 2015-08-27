package ami.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;
import org.joda.time.DateTime;

import ami.application.commands.amirequest.CreateAmiRequestCmd;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.events.amirequest.AmiRequestCreatedEvent;
import ami.application.events.amirequest.AmiRequestSavedAsDraftEvent;
import ami.domain.amirequest.AmiRequest;

public class AmiRequestAggregate extends AbstractAnnotatedAggregateRoot {

	@AggregateIdentifier
	private String id;
	
	//private String amiRequest;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	
	private boolean editable;
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime interpretationReadyComplete;
	
	// No-arg constructor, required by Axon
	public AmiRequestAggregate() {
	}

	@CommandHandler
	public AmiRequestAggregate(CreateAmiRequestCmd command) {
		apply(new AmiRequestCreatedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), 
				command.getHospitalName(),
				command.getHospitalId(),
				command.getHasBeenSavedAndSubmittedToRadiologist(),
				command.getInterpretationInProgress(),
				command.getInterpretationReadyForReview(),
				command.getInterpretationReadyComplete(),
				command.isEditable()));
	}
	
	@CommandHandler
	public AmiRequestAggregate(SaveAmiRequestAsDraftCmd command) {
		if(hasBeenSavedAndSubmittedToRadiologist()){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("Requests cannot be saved as draft save as draft once they have been submitted to the radiologist.");
		}
		apply(new AmiRequestSavedAsDraftEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName() ,
				 command.getHospitalId(),
				 command.getHasBeenSavedAndSubmittedToRadiologist(),
				 command.getInterpretationInProgress(),
				 command.getInterpretationReadyForReview(),
				 command.getInterpretationReadyComplete(),
				 command.isEditable()) );
	}




	@EventSourcingHandler
	public void on(AmiRequestCreatedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		
		this.hasBeenSavedAndSubmittedToRadiologist = event.getHasBeenSavedAndSubmittedToRadiologist(); 
		this.interpretationInProgress = event.getInterpretationInProgress() ;              
		this.interpretationReadyForReview = event.getInterpretationReadyForReview();          
		this.interpretationReadyComplete = event.getInterpretationReadyComplete();           
		this.editable   = event.isEditable();    
	}
	
	
	@EventSourcingHandler
	public void on(AmiRequestSavedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		
		this.hasBeenSavedAndSubmittedToRadiologist = event.getHasBeenSavedAndSubmittedToRadiologist(); 
		this.interpretationInProgress = event.getInterpretationInProgress() ;              
		this.interpretationReadyForReview = event.getInterpretationReadyForReview();          
		this.interpretationReadyComplete = event.getInterpretationReadyComplete();           
		this.editable   = event.isEditable(); 
	}
	
	private boolean hasBeenSavedAndSubmittedToRadiologist() {
		if ( hasBeenSavedAndSubmittedToRadiologist!=null ){
			return true;
		}
		return false;
	}
}

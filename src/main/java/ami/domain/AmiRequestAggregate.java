package ami.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import ami.application.commands.CreateAmiRequestCmd;
import ami.application.commands.SaveAmiRequestAsDraftCmd;
import ami.application.events.AmiRequestCreatedEvent;
import ami.application.events.AmiRequestSavedAsDraftEvent;

public class AmiRequestAggregate extends AbstractAnnotatedAggregateRoot {

	@AggregateIdentifier
	private String id;
	
	private String amiRequest;
	private String userName;
	private String hospitalName;
	private boolean hasBeenSaved;
	

	// No-arg constructor, required by Axon
	public AmiRequestAggregate() {
	}

	@CommandHandler
	public AmiRequestAggregate(CreateAmiRequestCmd command) {
		apply(new AmiRequestCreatedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName()) );
	}
	
	@CommandHandler
	public AmiRequestAggregate(SaveAmiRequestAsDraftCmd command) {
		if(hasBeenSaved){
			// should never be here, the UI should disable the save as draft button once the request is saved.
			throw new RuntimeException("Cannot save as draft once the request has been created.");
		}
		apply(new AmiRequestSavedAsDraftEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName()) );
	}


	@EventSourcingHandler
	public void on(AmiRequestCreatedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
		this.hasBeenSaved = true;
	}
	
	
	@EventSourcingHandler
	public void on(AmiRequestSavedAsDraftEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
	}
}

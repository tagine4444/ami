package ami.domain;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import ami.application.commands.CreateAmiRequestCmd;
import ami.application.events.AmiRequestCreatedEvent;

public class AmiRequestAggregate extends AbstractAnnotatedAggregateRoot {

	@AggregateIdentifier
	private String id;
	
	private String amiRequest;
	private String userName;
	private String hospitalName;

	// No-arg constructor, required by Axon
	public AmiRequestAggregate() {
	}

	@CommandHandler
	public AmiRequestAggregate(CreateAmiRequestCmd command) {
		apply(new AmiRequestCreatedEvent(command.getId(),
				command.getAmiRequestJson() , command.getUserName(), command.getHospitalName()) );
	}


	@EventSourcingHandler
	public void on(AmiRequestCreatedEvent event) {
		this.id = event.getId();
		this.amiRequest = event.getAmiRequestJson();
		this.userName = event.getUserName();
		this.hospitalName = event.getHospitalName();
	}
}

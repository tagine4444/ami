package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.amicase.Amendment;

public class AmendCaseCmd {

	@TargetAggregateIdentifier
    private final String id;
	private final Amendment amendment;
    
    
    public AmendCaseCmd(String id, Amendment amendment) {
    	this.id = id;
    	this.amendment =amendment;
	}


	public String getId() {
		return id;
	}


	public Amendment getAmendment() {
		return amendment;
	}

}

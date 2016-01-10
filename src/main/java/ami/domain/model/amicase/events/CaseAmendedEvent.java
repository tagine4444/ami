package ami.domain.model.amicase.events;

import ami.domain.model.amicase.Amendment;

public class CaseAmendedEvent {

	private final String id;
	private final Amendment amendment;
    
    
    public CaseAmendedEvent(String id, Amendment amendment) {
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

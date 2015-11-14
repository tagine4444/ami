package ami.application.events.amirequest;

import org.joda.time.DateTime;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class NewAmiRequestSubmittedEvent {

	private final String id;
	private final AmiRequest amiRequestJson;
	private final String userName;
	private final String hospitalName;
	private final String hospitalId;
	
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
//	private boolean editable;
 
    public NewAmiRequestSubmittedEvent(String id, AmiRequest amiRequestJson, 
    		String userName, 
    		String hospitalName, 
    		String hospitalId,
    		DateTime hasBeenSavedAndSubmittedToRadiologist
//    		,boolean editable 
    		) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId  = hospitalId;
        
        this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist; 
//		this.editable   = editable;                  
    }
    
    public String getUserName() {
		return userName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getId() {
		return id;
	}

	public AmiRequest getAmiRequestJson() {
		return amiRequestJson;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	

//	public boolean isEditable() {
//		return editable;
//	}

	public DateTime getHasBeenSavedAndSubmittedToRadiologist() {
		return hasBeenSavedAndSubmittedToRadiologist;
	}

}

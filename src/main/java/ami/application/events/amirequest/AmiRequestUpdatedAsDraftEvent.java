package ami.application.events.amirequest;

import org.joda.time.DateTime;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class AmiRequestUpdatedAsDraftEvent {

	private final String id;
	private final AmiRequest amiRequestJson;
	private final String userName;
	private String hospitalName;
	private String hospitalId;
	
//	private DateTime hasBeenSavedAndSubmittedToRadiologist;
//	private DateTime interpretationInProgress;
//	private DateTime interpretationReadyForReview;
//	private DateTime interpretationReadyComplete;
//	private boolean editable;
    private DateTime dateTime;
    
        
       

    public AmiRequestUpdatedAsDraftEvent(String id, AmiRequest amiRequestJson, String userName, String hospitalName,String hospitalId,
//    		boolean editable,
    		DateTime dateTime ) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId   = hospitalId;
        
//		this.editable   = editable;    
		this.dateTime = dateTime;
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

	public DateTime getDateTime() {
		return dateTime;
	}

//	public DateTime getHasBeenSavedAndSubmittedToRadiologist() {
//		return hasBeenSavedAndSubmittedToRadiologist;
//	}
//
//	public DateTime getInterpretationInProgress() {
//		return interpretationInProgress;
//	}
//
//	public DateTime getInterpretationReadyForReview() {
//		return interpretationReadyForReview;
//	}
//
//	public DateTime getInterpretationReadyComplete() {
//		return interpretationReadyComplete;
//	}
}

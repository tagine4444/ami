package ami.domain.model.amicase.events;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class AmiRequestSavedAsDraftEvent {

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
 
    
        
       

    public AmiRequestSavedAsDraftEvent(String id, AmiRequest amiRequestJson, String userName, String hospitalName,String hospitalId
//    		DateTime hasBeenSavedAndSubmittedToRadiologist, 
//    		DateTime interpretationInProgress,              
//    		DateTime interpretationReadyForReview,          
//    		DateTime interpretationReadyComplete,           
//    		,boolean editable 
    		) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId   = hospitalId;
        
//        this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist; 
//		this.interpretationInProgress = interpretationInProgress ;              
//		this.interpretationReadyForReview = interpretationReadyForReview;          
//		this.interpretationReadyComplete = interpretationReadyComplete;           
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
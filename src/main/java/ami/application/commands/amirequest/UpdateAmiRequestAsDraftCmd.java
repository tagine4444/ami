package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.amicase.amirequest.AmiRequest;

public class UpdateAmiRequestAsDraftCmd {
	
	@TargetAggregateIdentifier
    private final String id;
    private final AmiRequest amiRequestJson;
    private final String userName;
    private final String hospitalName;
    private final String hospitalId;
    
//    private DateTime hasBeenSavedAndSubmittedToRadiologist;
//   	private DateTime interpretationInProgress;
//   	private DateTime interpretationReadyForReview;
//   	private DateTime interpretationReadyComplete;
//   	private boolean editable;
   	private DateTime dateTime;


    public UpdateAmiRequestAsDraftCmd(String id, AmiRequest amiRequestJson, String userName,   
    		String hospitalName, String hospitalId,
    		
//    		DateTime hasBeenSavedAndSubmittedToRadiologist, 
//    		DateTime interpretationInProgress,              
//    		DateTime interpretationReadyForReview,          
//    		DateTime interpretationReadyComplete,           
//    		boolean editable,
    		DateTime dateTime ) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId =  hospitalId;
        this.dateTime = dateTime;
        
//        this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist; 
//   		this.interpretationInProgress = interpretationInProgress ;              
//   		this.interpretationReadyForReview = interpretationReadyForReview;          
//   		this.interpretationReadyComplete = interpretationReadyComplete;           
//   		this.editable   = editable;       
    }

    public String getUserName() {
		return userName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public AmiRequest getAmiRequestJson() {
        return amiRequestJson;
    }

    public String getId() {
        return id;
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

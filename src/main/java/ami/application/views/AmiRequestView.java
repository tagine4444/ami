package ami.application.views;

import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;

import ami.domain.amirequest.AmiRequest;

public class AmiRequestView {
	
//	@JsonRawValue
//	private String amiRequest;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	private String hospitalId;
	private DateTime time ;
	
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime interpretationReadyComplete;
	private boolean editable;
	
	
	public AmiRequestView( AmiRequest amiRequest, String userName,
			String hospitalName,
			String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime interpretationReadyComplete,           
    		boolean editable,
			@Timestamp DateTime time ) {
		
		this.amiRequest    = amiRequest;   
		this.userName      = userName;     
		this.hospitalId    = hospitalId;
		this.hospitalName  = hospitalName; 
		
		this.editable =  editable;
		this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist ;
		this.interpretationInProgress = interpretationInProgress ;
		this.interpretationReadyForReview = interpretationReadyForReview ;
		this.interpretationReadyComplete =  interpretationReadyComplete;
		
		this.time = time;
	}

	public AmiRequest getAmiRequest() {
		return amiRequest;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getHospitalName() {
		return hospitalName;
	}

	public DateTime getTime() {
		return time;
	}

	public boolean isEditable() {
		return editable;
	}

	

	public String getHospitalId() {
		return hospitalId;
	}

	public DateTime getHasBeenSavedAndSubmittedToRadiologist() {
		return hasBeenSavedAndSubmittedToRadiologist;
	}

	public DateTime getInterpretationInProgress() {
		return interpretationInProgress;
	}

	public DateTime getInterpretationReadyForReview() {
		return interpretationReadyForReview;
	}

	public DateTime getInterpretationReadyComplete() {
		return interpretationReadyComplete;
	}

	

}

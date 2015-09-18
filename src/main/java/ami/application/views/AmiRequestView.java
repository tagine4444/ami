package ami.application.views;

import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import ami.domain.amirequest.AmiRequest;
import ami.domain.amirequest.FileUploadInfo;

public class AmiRequestView {
	
//	@JsonRawValue
//	private String amiRequest;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	private String hospitalId;
	
	private String creationDate ;
	private String updateDate ;
	private String updateUser;
	
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime interpretationReadyComplete;
	private boolean editable;
	private List<FileUploadInfo> fileUploads ;
	
	
	public AmiRequestView( AmiRequest amiRequest, String userName,
			String hospitalName,
			String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime interpretationReadyComplete,           
    		boolean editable,
			String creationDate,
			List<FileUploadInfo> fileUploads,
			String updateUser,
			String updateDate) {
		
		this.amiRequest    = amiRequest;   
		this.userName      = userName;     
		this.hospitalId    = hospitalId;
		this.hospitalName  = hospitalName; 
		
		this.editable =  editable;
		this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist ;
		this.interpretationInProgress = interpretationInProgress ;
		this.interpretationReadyForReview = interpretationReadyForReview ;
		this.interpretationReadyComplete =  interpretationReadyComplete;
		this.creationDate = creationDate;
		this.fileUploads = fileUploads;
		
		this.updateDate = null;
		this.updateUser = null;
	}
	
	public void update(AmiRequestView updatedView, String updateDate, String updateUser){
		
		this.updateDate = updateDate;
		this.updateUser = updateUser;
		
		this.amiRequest    = updatedView.getAmiRequest();   
		this.userName      = updatedView.getUserName();     
		this.hospitalId    = updatedView.getHospitalId();
		this.hospitalName  = updatedView.getHospitalName(); 
		
		this.editable =  updatedView.isEditable();
		this.hasBeenSavedAndSubmittedToRadiologist = updatedView.getHasBeenSavedAndSubmittedToRadiologist() ;
		this.interpretationInProgress = updatedView.getInterpretationInProgress() ;
		this.interpretationReadyForReview = updatedView.getInterpretationReadyForReview() ;
		this.interpretationReadyComplete =  updatedView.getInterpretationReadyComplete();
		
		this.creationDate = updatedView.getCreationDate();
		this.fileUploads = updatedView.getFileUploads();
		
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

	public String getCreationDate() {
		return creationDate;
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

	public List<FileUploadInfo> getFileUploads() {
		return fileUploads;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

}

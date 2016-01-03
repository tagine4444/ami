package ami.infrastructure.database.model;

import java.util.List;

import org.joda.time.DateTime;

import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;

public class AmiRequestView {
	
//	@JsonRawValue
	private String caseNumber;
	private AmiRequest amiRequest;
	private String userName;
	private String hospitalName;
	private String hospitalId;
	
	private String contract;
	private String accountSize;
	private String radiographicInterpretation; 	// ONLY in non contract long form
	private String radiographicImpression;    	// both contract and non contract
	private String recommendation;				// both contract and non contract
	
	
	private String creationDateString ;
	private DateTime creationDate ;
	private String updateDateString ;
	private DateTime updateDate ;
	private String updateUser;
	
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime caseClosed;
	private boolean editable;
	private List<FileUploadInfo> fileUploads ;
	
	
	
	public AmiRequestView( String caseNumber, AmiRequest amiRequest, String userName,
			String hospitalName,
			String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime caseClosed,           
    		boolean editable,
			String creationDateString,
			DateTime creationDate,
			List<FileUploadInfo> fileUploads,
			String updateUser,
			String updateDateString,
			DateTime updateDate, 
			String contract,
			String accountSize) {
		
		this.caseNumber = caseNumber;
		this.amiRequest    = amiRequest;   
		this.userName      = userName;     
		this.hospitalId    = hospitalId;
		this.hospitalName  = hospitalName; 
		this.contract = contract;
		this.accountSize = accountSize;
		
		this.editable =  editable;
		this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist ;
		this.interpretationInProgress = interpretationInProgress ;
		this.interpretationReadyForReview = interpretationReadyForReview ;
		this.caseClosed =  caseClosed;
		this.creationDateString = creationDateString;
		this.creationDate = creationDate;
		this.fileUploads = fileUploads;
		
		this.updateDateString = updateDateString;
		this.updateDate = updateDate;
		this.updateUser = null;
		
	}
	
	
//	public void update(AmiRequestView updatedView, String updateDateString, String updateUser , DateTime updateDate){
//		
//		this.updateDate = updateDate;
//		this.updateDateString = updateDateString;
//		this.updateUser = updateUser;
//		
//		this.amiRequest    = updatedView.getAmiRequest();   
//		this.userName      = updatedView.getUserName();     
//		this.hospitalId    = updatedView.getHospitalId();
//		this.hospitalName  = updatedView.getHospitalName(); 
//		
//		this.contract = updatedView.getContract();
//		this.accountSize = updatedView.getAccountSize();
//		
//		this.editable =  updatedView.isEditable();
//		this.hasBeenSavedAndSubmittedToRadiologist = updatedView.getHasBeenSavedAndSubmittedToRadiologist() ;
//		this.interpretationInProgress = updatedView.getInterpretationInProgress() ;
//		this.interpretationReadyForReview = updatedView.getInterpretationReadyForReview() ;
//		this.interpretationReadyComplete =  updatedView.getInterpretationReadyComplete();
//		
//		this.creationDateString = updatedView.getCreationDateString();
//		this.fileUploads = updatedView.getFileUploads();
//		
//		
//		
//	}

	public AmiRequest getAmiRequest() {
		return amiRequest;
	}
	
	public String getUserName() {
		return userName;
	}
	public String getHospitalName() {
		return hospitalName;
	}

	public String getCreationDateString() {
		return creationDateString;
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

	public DateTime getCaseClosed() {
		return caseClosed;
	}

	public List<FileUploadInfo> getFileUploads() {
		return fileUploads;
	}

	public String getUpdateDateString() {
		return updateDateString;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public DateTime getCreationDate() {
		return creationDate;
	}

	public DateTime getUpdateDate() {
		return updateDate;
	}


	public String getCaseNumber() {
		return caseNumber;
	}


	public String getContract() {
		return contract;
	}


	public String getAccountSize() {
		return accountSize;
	}


	public String getRadiographicInterpretation() {
		return radiographicInterpretation;
	}


	public String getRadiographicImpression() {
		return radiographicImpression;
	}


	public String getRecommendation() {
		return recommendation;
	}

}

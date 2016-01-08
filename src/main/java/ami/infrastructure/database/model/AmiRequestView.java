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
	
	private String updateUser;
	
	private DateTime creationDate ;
	private DateTime updateDate ;
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime caseClosed;
	
	private String creationDateString ;
	private String updateDateString ;
	private String hasBeenSavedAndSubmittedToRadiologistString;
	private String interpretationInProgressString;
	private String interpretationReadyForReviewString;
	private String caseClosedString;
	
	
	private boolean editable;
	private List<FileUploadInfo> fileUploads ;
	
	
	
	public AmiRequestView( String caseNumber, AmiRequest amiRequest, String userName,
			String hospitalName,
			String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime caseClosed,           
			DateTime creationDate,
			List<FileUploadInfo> fileUploads,
			String updateUser,
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
		
		this.editable 		=  hasBeenSavedAndSubmittedToRadiologist==null;
		this.fileUploads = fileUploads;
		this.updateUser = null;
		
		this.creationDate = creationDate;
		this.updateDate = updateDate;
		this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist ;
		this.interpretationInProgress = interpretationInProgress ;
		this.interpretationReadyForReview = interpretationReadyForReview ;
		this.caseClosed =  caseClosed;
		
		 if(creationDate!=null){
			 this.creationDateString = creationDate.toString();
		 }
		 
		 if(updateDate!=null){
			 this.updateDateString = updateDate.toString();
		 }
		 
		 if(hasBeenSavedAndSubmittedToRadiologist!=null){
			 this.hasBeenSavedAndSubmittedToRadiologistString = hasBeenSavedAndSubmittedToRadiologist.toString();
		 }
        
		 if(interpretationInProgress!=null){
			 this.interpretationInProgressString = interpretationInProgress.toString();
		 }
		 if(interpretationReadyForReview!=null){
			 this.interpretationReadyForReviewString = interpretationReadyForReview.toString();
		 }
		 if(caseClosed!=null){
			 this.caseClosedString = caseClosed.toString();
		 }
		 
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


	public String getHasBeenSavedAndSubmittedToRadiologistString() {
		return hasBeenSavedAndSubmittedToRadiologistString;
	}


	public String getInterpretationInProgressString() {
		return interpretationInProgressString;
	}


	public String getInterpretationReadyForReviewString() {
		return interpretationReadyForReviewString;
	}


	public String getCaseClosedString() {
		return caseClosedString;
	}

}

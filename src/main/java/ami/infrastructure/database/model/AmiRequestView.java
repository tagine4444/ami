package ami.infrastructure.database.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import ami.domain.model.amicase.Amendment;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;

@Document
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
	private String draftCaseDeletedBy;
	
	private DateTime creationDate ;
	private DateTime updateDate ;
	private DateTime hasBeenSavedAndSubmittedToRadiologist;
	private DateTime interpretationInProgress;
	private DateTime interpretationReadyForReview;
	private DateTime caseClosed;
	private DateTime accountingDone;
	private DateTime draftCaseDeletedTime;
	
	private boolean editable;
	private List<FileUploadInfo> fileUploads ;
	private List<Amendment> amendments ;
	
	
	
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
			String accountSize,
			DateTime accountingDone) {
		
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
		this.amendments = new ArrayList<Amendment>();
		 
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

	public DateTime getAccountingDone() {
		return accountingDone;
	}

	public List<Amendment> getAmendments() {
		return amendments;
	}


	public DateTime getDraftCaseDeletedTime() {
		return draftCaseDeletedTime;
	}


	public String getDraftCaseDeletedBy() {
		return draftCaseDeletedBy;
	}
}

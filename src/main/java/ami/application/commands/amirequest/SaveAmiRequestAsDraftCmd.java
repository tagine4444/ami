package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class SaveAmiRequestAsDraftCmd {
	
	@TargetAggregateIdentifier
    private final String id;
    private final AmiRequest amiRequestJson;
    private final String userName;
    private final String hospitalName;
    private final String hospitalId;
    private final String contract;
	private final String accountSize;
    

    public SaveAmiRequestAsDraftCmd(String id, AmiRequest amiRequestJson, String userName,   
    		String hospitalName, String hospitalId, String contract, String accountSize
    		) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId =  hospitalId;
        this.contract = contract;
        this.accountSize = accountSize;
    
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

	public String getContract() {
		return contract;
	}

	public String getAccountSize() {
		return accountSize;
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

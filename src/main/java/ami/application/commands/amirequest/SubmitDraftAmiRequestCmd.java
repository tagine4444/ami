package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class SubmitDraftAmiRequestCmd {

	@TargetAggregateIdentifier
    private final String id;
    private final AmiRequest amiRequestJson;
    private final String userName;
    private String hospitalName;
    private final String hospitalId;
    private final String contract;
	private final String accountSize;
    
    private DateTime hasBeenSavedAndSubmittedToRadiologist;
//	private boolean editable;
	private DateTime dateTime;

    public SubmitDraftAmiRequestCmd(String id, AmiRequest amiRequestJson, String userName,   
    		String hospitalName, String hospitalId,
    		DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime dateTime,
    		String contract, String accountSize
    		) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId = hospitalId;
        this.contract = contract;
        this.accountSize = accountSize;
        
        this.hasBeenSavedAndSubmittedToRadiologist = hasBeenSavedAndSubmittedToRadiologist; 
		this.dateTime = dateTime;
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

	public DateTime getHasBeenSavedAndSubmittedToRadiologist() {
		return hasBeenSavedAndSubmittedToRadiologist;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public String getContract() {
		return contract;
	}

	public String getAccountSize() {
		return accountSize;
	}

}

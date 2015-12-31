package ami.application.commands.amirequest;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import org.joda.time.DateTime;

import ami.domain.model.amicase.amirequest.AmiRequest;

public class UpdateAmiRequestAsDraftCmd {
	
	@TargetAggregateIdentifier
    private final String id;
    private final AmiRequest amiRequestJson;
    private final String userName;
    private final String hospitalName;
    private final String hospitalId;
    private final String contract;
	private final String accountSize;

   	private DateTime dateTime;


    public UpdateAmiRequestAsDraftCmd(String id, AmiRequest amiRequestJson, String userName,   
    		String hospitalName, String hospitalId,
    		DateTime dateTime,
    		String contract, String accountSize) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
        this.hospitalId =  hospitalId;
        this.dateTime = dateTime;
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

package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateHospitalContractCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newContract;

    public UpdateHospitalContractCmd(String hospitalId, String userName, String newContract) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newContract = newContract;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewContract() {
		return newContract;
	}
}

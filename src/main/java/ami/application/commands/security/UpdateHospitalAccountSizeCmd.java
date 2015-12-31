package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateHospitalAccountSizeCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newAccountSize;

    public UpdateHospitalAccountSizeCmd(String hospitalId, String userName, String newAccountSize) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newAccountSize = newAccountSize;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewAccountSize() {
		return newAccountSize;
	}
}

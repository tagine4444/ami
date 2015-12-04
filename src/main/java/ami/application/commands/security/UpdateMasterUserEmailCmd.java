package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateMasterUserEmailCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newEmail;

    public UpdateMasterUserEmailCmd(String hospitalId, String userName, String newEmail) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newEmail = newEmail;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewEmail() {
		return newEmail;
	}
}

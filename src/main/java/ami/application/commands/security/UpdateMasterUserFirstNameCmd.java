package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateMasterUserFirstNameCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newFirstName;

    public UpdateMasterUserFirstNameCmd(String hospitalId, String userName, String newFirstName) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newFirstName = newFirstName;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewFirstName() {
		return newFirstName;
	}
}

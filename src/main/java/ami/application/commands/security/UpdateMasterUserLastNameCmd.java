package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateMasterUserLastNameCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newLastName;

    public UpdateMasterUserLastNameCmd(String hospitalId, String userName, String newLastName) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newLastName = newLastName;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getNewLastName() {
		return newLastName;
	}
}

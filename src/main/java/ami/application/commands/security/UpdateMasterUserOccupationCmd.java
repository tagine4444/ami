package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateMasterUserOccupationCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newOccupation;

    public UpdateMasterUserOccupationCmd(String hospitalId, String userName, String newOccupation) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newOccupation = newOccupation;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getNewOccupation() {
		return newOccupation;
	}

}

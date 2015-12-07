package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class SwitchMasterUserCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
	
    private final String newMasterUser;
    

    public SwitchMasterUserCmd(String hospitalId, String newMasterUser) {
        this.hospitalId = hospitalId;
        this.newMasterUser = newMasterUser;
    }


	public String getHospitalId() {
		return hospitalId;
	}

	public String getNewMasterUser() {
		return newMasterUser;
	}
}

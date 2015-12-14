package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateHospitalAcronymCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newAcronym;

    public UpdateHospitalAcronymCmd(String hospitalId, String userName, String newAcronym) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newAcronym = newAcronym;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewAcronym() {
		return newAcronym;
	}
}

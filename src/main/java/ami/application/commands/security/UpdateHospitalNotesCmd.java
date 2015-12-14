package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class UpdateHospitalNotesCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final String newNotes;

    public UpdateHospitalNotesCmd(String hospitalId, String userName, String newNotes) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newNotes = newNotes;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getNewNotes() {
		return newNotes;
	}
}

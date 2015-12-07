package ami.domain.model.security.events;

public class MasterUserSwitchedAbortedCauseAlreadyMasterUserEvent {

	private final String hospitalId;
	private final String newMasterUser;

	public MasterUserSwitchedAbortedCauseAlreadyMasterUserEvent(String hospitalId, String newMasterUser) {
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

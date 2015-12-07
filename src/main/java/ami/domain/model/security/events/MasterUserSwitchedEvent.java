package ami.domain.model.security.events;

public class MasterUserSwitchedEvent {

	private final String hospitalId;
	private final String newMasterUser;

	public MasterUserSwitchedEvent(String hospitalId, String newMasterUser) {
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

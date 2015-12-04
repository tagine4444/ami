package ami.domain.model.security.events;

public class MasterUserEmailUpdatedEvent {

	private final String hospitalId;
	private final String userName;
	private final String newEmail;

	public MasterUserEmailUpdatedEvent(String hospitalId, String userName, String newEmail) {
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

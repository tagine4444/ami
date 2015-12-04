package ami.domain.model.security.events;

public class MasterUserFirstNameUpdatedEvent {

	private final String hospitalId;
	private final String userName;
	private final String newFirstName;

	public MasterUserFirstNameUpdatedEvent(String hospitalId, String userName, String newFirstName) {
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

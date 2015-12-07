package ami.domain.model.security.events;

public class MasterUserLastNameUpdatedEvent {

	private final String hospitalId;
	private final String userName;
	private final String newLastName;

	public MasterUserLastNameUpdatedEvent(String hospitalId, String userName, String newLastName) {
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

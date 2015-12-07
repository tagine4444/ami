package ami.domain.model.security.events;

public class MasterUserOccupationUpdatedEvent {

	private final String hospitalId;
	private final String userName;
	private final String newOccupation;

	public MasterUserOccupationUpdatedEvent(String hospitalId, String userName, String newOccupation) {
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

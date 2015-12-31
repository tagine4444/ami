package ami.domain.model.security.events;


public class HospitalAccountSizeUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final String newAccountSize;

    public HospitalAccountSizeUpdatedEvent(String hospitalId, String userName, String newAccountSize) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newAccountSize = newAccountSize;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getNewAccountSize() {
		return newAccountSize;
	}
}

package ami.domain.model.security.events;


public class HospitalAcronymUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final String newAcronym;

    public HospitalAcronymUpdatedEvent(String hospitalId, String userName, String newAcronym) {
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

package ami.domain.model.security.events;


public class HospitalNotesUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final String newNotes;

    public HospitalNotesUpdatedEvent(String hospitalId, String userName, String newNotes) {
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

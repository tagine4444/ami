package ami.application.events.amirequest;

public class AmiRequestCreatedEvent {

	private final String id;
	private final String amiRequestJson;
	private final String userName;
	private final String hospitalName;

    public AmiRequestCreatedEvent(String id, String amiRequestJson, String userName, String hospitalName) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
    }
    
    public String getUserName() {
		return userName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getId() {
		return id;
	}

	public String getAmiRequestJson() {
		return amiRequestJson;
	}
}

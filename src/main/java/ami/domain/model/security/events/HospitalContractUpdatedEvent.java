package ami.domain.model.security.events;


public class HospitalContractUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final String newContract;

    public HospitalContractUpdatedEvent(String hospitalId, String userName, String newContract) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newContract = newContract;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getNewContract() {
		return newContract;
	}
}

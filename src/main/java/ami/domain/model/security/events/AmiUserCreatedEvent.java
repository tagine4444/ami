package ami.domain.model.security.events;

import ami.domain.model.security.amiusers.AmiUser;

public class AmiUserCreatedEvent {
	
	private final String hospitalId;
	private final String hospitalName;
    private final AmiUser amiUser;

    public AmiUserCreatedEvent(String hospitalId,String hospitalName,  AmiUser amiUser) {
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.amiUser = amiUser;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public AmiUser getAmiUser() {
		return amiUser;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	

}

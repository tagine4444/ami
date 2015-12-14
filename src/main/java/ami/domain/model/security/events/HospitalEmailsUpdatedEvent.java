package ami.domain.model.security.events;

import java.util.List;

import ami.domain.model.security.hospitals.Email;

public class HospitalEmailsUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final List<Email> newEmailList;

    public HospitalEmailsUpdatedEvent(String hospitalId, String userName, List<Email> newEmailList) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newEmailList = newEmailList;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public List<Email> getNewEmailList() {
		return newEmailList;
	}

}

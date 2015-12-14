package ami.domain.model.security.events;

import java.util.List;

import ami.domain.model.security.hospitals.Phone;

public class HospitalPhonesUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final List<Phone> newPhoneList;

    public HospitalPhonesUpdatedEvent(String hospitalId, String userName, List<Phone> newPhoneList) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newPhoneList = newPhoneList;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public List<Phone> getNewPhoneList() {
		return newPhoneList;
	}

}

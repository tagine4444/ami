package ami.domain.model.security.events;

import java.util.List;

import ami.domain.model.security.hospitals.Address;

public class HospitalAddressesUpdatedEvent {
	
    private final String hospitalId;
    private final String userName;
    private final List<Address> newAddressList;

    public HospitalAddressesUpdatedEvent(String hospitalId, String userName, List<Address> newAddressList) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newAddressList = newAddressList;
    }

	public String getHospitalId() {
		return hospitalId;
	}

	public String getUserName() {
		return userName;
	}

	public List<Address> getNewAddressList() {
		return newAddressList;
	}

}

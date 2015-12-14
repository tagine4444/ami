package ami.application.commands.security;

import java.util.List;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.security.hospitals.Address;

public class UpdateHospitalAddressesCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final List<Address> newAddressList;

    public UpdateHospitalAddressesCmd(String hospitalId, String userName, List<Address> newAddressList) {
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

package ami.application.commands.security;

import java.util.List;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.security.hospitals.Phone;

public class UpdateHospitalPhonesCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final List<Phone> newPhoneList;

    public UpdateHospitalPhonesCmd(String hospitalId, String userName, List<Phone> newPhoneList) {
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

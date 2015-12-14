package ami.application.commands.security;

import java.util.List;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.security.hospitals.Email;

public class UpdateHospitalEmailsCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
    private final String userName;
    private final List<Email> newEmailList;

    public UpdateHospitalEmailsCmd(String hospitalId, String userName, List<Email> newEmailList) {
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

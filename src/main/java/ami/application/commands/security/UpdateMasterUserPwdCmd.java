package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.security.amiusers.AmiUser;

public class UpdateMasterUserPwdCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
	
    private final String userName;
    private final String newPwd;
    

    public UpdateMasterUserPwdCmd(String hospitalId, String userName, String newPwd) {
        this.hospitalId = hospitalId;
        this.userName = userName;
        this.newPwd = newPwd;
    }

	public String getHospitalId() {
		return hospitalId;
	}



	public String getUserName() {
		return userName;
	}



	public String getNewPwd() {
		return newPwd;
	}
}

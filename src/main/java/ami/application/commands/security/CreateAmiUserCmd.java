package ami.application.commands.security;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import ami.domain.model.security.amiusers.AmiUser;

public class CreateAmiUserCmd {
	
	@TargetAggregateIdentifier
    private final String hospitalId;
	private final String hospitalName;
	
    private final AmiUser amiUser;

    public CreateAmiUserCmd(String hospitalId, String hospitalName,AmiUser amiUser) {
        this.hospitalId = hospitalId;
        this.hospitalName  = hospitalName;
        this.amiUser = amiUser;
        
    }

	
	public AmiUser getAmiUser() {
		return amiUser;
	}


	public String getHospitalId() {
		return hospitalId;
	}


	public String getHospitalName() {
		return hospitalName;
	}

}

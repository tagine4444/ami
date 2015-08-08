package ami.application.commands;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateAmiRequestCmd {

	@TargetAggregateIdentifier
    private final String id;
    private final String amiRequestJson;
    private final String userName;
    private String hospitalName;

    public CreateAmiRequestCmd(String id, String amiRequestJson, String userName,   String hospitalName) {
        this.id = id;
        this.amiRequestJson = amiRequestJson;
        this.userName = userName;
        this.hospitalName = hospitalName;
    }

    public String getUserName() {
		return userName;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public String getAmiRequestJson() {
        return amiRequestJson;
    }

    public String getId() {
        return id;
    }
}

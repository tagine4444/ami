package ami.domain.model.security.events;

public class MasterUserPwdUpdatedEvent {

	private final String hospitalId;
	private final String userName;
	private final String newPwd;

	public MasterUserPwdUpdatedEvent(String hospitalId, String userName, String newPwd) {
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

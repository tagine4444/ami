package ami.application.views;

import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;

import ami.domain.amiusers.AmiUser;

public class AmiUserView {
	
	private AmiUser amiUser;
	private String hospitalId;
	private String hospitalName;
	private DateTime time ;
	public boolean masterUser;
	
	public AmiUserView(AmiUser amiUser,String hospitalId, String hospitalName, @Timestamp DateTime time , boolean masterUser) {
		
		this.amiUser    = amiUser;  
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.time = time;
		this.masterUser = masterUser;
	}

	public AmiUser getAmiUser() {
		return amiUser;
	}

	public DateTime getTime() {
		return time;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public boolean isMasterUser() {
		return masterUser;
	}
	
}

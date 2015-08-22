package ami.domain.amiusers;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class AmiUser {

	private String user;
	private String pwd;
	private String hospitalName;
	private long hospitalId;
	private  DateTime creationDate;
	private  String createdBy;
	private  DateTime deactivationDate;
	private  String deactivatedBy;
	private String deactivationReason;
	
	private List<? extends GrantedAuthority> role;
	
	public AmiUser(){
		
	}
	public AmiUser(String user,String pwd, String hospitalName, long hospitalId, DateTime creationDate, List<? extends GrantedAuthority> role) {
		this.user = user;
		this.pwd = pwd;
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalId;
		this.creationDate =creationDate;
		this.role = role;
	}
	
	public String getHospitalName() {
		return hospitalName;
	}

	public List<? extends GrantedAuthority> getRole() {
		return role;
	}

	public String getUser() {
		return user;
	}
	
	public String getPwd() {
		return pwd;
	}

	public void blurPassword(){
		this.pwd = "";
	}
	
	
	@Override
	public String toString() {
		return "AmiUser [  user=" + user +", role=" + role.get(0).getAuthority() +"]";
	}
	public long getHospitalId() {
		return hospitalId;
	}
	public DateTime getCreationDate() {
		return creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public DateTime getDeactivationDate() {
		return deactivationDate;
	}
	public String getDeactivatedBy() {
		return deactivatedBy;
	}
	public String getDeactivationReason() {
		return deactivationReason;
	}
	
}

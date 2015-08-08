package ami.model.users;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class AmiUser {

	private String id;
	private String user;
	private String pwd;
	private String hospitalName;
	private List<? extends GrantedAuthority> role;
	
	
	public AmiUser(String user,String pwd, String hospitalName, List<? extends GrantedAuthority> role) {
		this.user = user;
		this.pwd = pwd;
		this.hospitalName = hospitalName;
		
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

	public String getId() {
		return id;
	}
	
	public void blurPassword(){
		this.pwd = "";
	}
	
	
	@Override
	public String toString() {
		return "AmiUser [id=" + id + ", user=" + user +", role=" + role.get(0).getAuthority() +"]";
	}
	
}

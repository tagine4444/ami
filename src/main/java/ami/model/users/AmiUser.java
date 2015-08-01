package ami.model.users;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class AmiUser {

	private String id;
	private String user;
	private String pwd;
	private List<? extends GrantedAuthority> role;
	
	
	public AmiUser(String user,String pwd, List<? extends GrantedAuthority> role) {
		this.user = user;
		this.pwd = pwd;
		this.role = role;
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
	
	
	@Override
	public String toString() {
		return "AmiUser [id=" + id + ", user=" + user +", role=" + role.get(0).getAuthority() +"]";
	}
	
}

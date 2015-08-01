package ami.model.users;

import org.springframework.security.core.GrantedAuthority;

public class AmiUserAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public String getAuthority() {
		return AmiAuthtorities.AMI_USER;
	}

}

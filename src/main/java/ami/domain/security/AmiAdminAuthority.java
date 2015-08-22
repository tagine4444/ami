package ami.domain.security;

import org.springframework.security.core.GrantedAuthority;

public class AmiAdminAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public String getAuthority() {
		return AmiAuthtorities.AMI_ADMIN;
	}

}

package ami.domain.amiusers;

import org.springframework.security.core.GrantedAuthority;

import ami.domain.security.AmiAuthtorities;

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

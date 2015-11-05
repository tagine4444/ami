package ami.domain.security;

import org.springframework.security.core.GrantedAuthority;

public class AmiMasterAuthority  implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public String getAuthority() {
		return AmiAuthtorities.AMI_MASTER_USER;
	}

}

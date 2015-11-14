package ami.application.services.security;

import ami.application.views.AmiUserView;
import ami.domain.security.amiusers.AmiUser;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmiUserService {
	
	void createAmiUser(String hospitalId,String hospitalName, AmiUser amiUser) throws JsonProcessingException ;
	void createAmiUserView(String hospitalId,String hospitalName, AmiUser amiUser)
			throws JsonProcessingException;
	AmiUserView findAmiUser(String userName);

}

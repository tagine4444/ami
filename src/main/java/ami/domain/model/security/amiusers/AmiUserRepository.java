package ami.domain.model.security.amiusers;

import ami.infrastructure.database.model.AmiUserView;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmiUserRepository {
	
	void createAmiUser(String hospitalId,String hospitalName, AmiUser amiUser) throws JsonProcessingException ;
	void createAmiUserView(String hospitalId,String hospitalName, AmiUser amiUser)
			throws JsonProcessingException;
	AmiUserView findAmiUser(String userName);

}

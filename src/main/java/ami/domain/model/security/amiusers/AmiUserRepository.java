package ami.domain.model.security.amiusers;

import ami.infrastructure.database.model.AmiUserView;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmiUserRepository {
	
	void createAmiUser(String hospitalId,String hospitalName, AmiUser amiUser) throws JsonProcessingException ;
	void createAmiUserView(String hospitalId,String hospitalName, AmiUser amiUser)throws JsonProcessingException;
	AmiUserView findAmiUser(String userName);
	
	AmiUserView authenticate(String userName, String pwd);
	
	void updateMasterUserPwd(String hospitalId, String user, String newPwd) throws JsonProcessingException;
	void upateMasterUserPwd(String userName, String newPwd);
	void upateMasterUserEmail(String userName, String newEmail);
	void upateMasterUserFirstName(String userName, String newFirstName);
	void upateMasterUserLastName(String userName, String newLastName);
	void upateMasterUserOccupation(String userName, String newOccupation);
	void switchMasterUserService(String hospitalId, String newMasterUser);

}

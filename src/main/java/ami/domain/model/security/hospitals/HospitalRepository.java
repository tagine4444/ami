package ami.domain.model.security.hospitals;

import java.util.List;

import org.joda.time.DateTime;

import ami.domain.model.security.amiusers.AmiUser;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface HospitalRepository {

	
	List<HospitalView> getAllHospitals() ;
	HospitalView findHospital(String hospitalId) ;
	Hospital findHospitalbyName(String name) ;
	void createHospitalView(Hospital hospital, DateTime hospitalActivationDate) throws JsonProcessingException ;
	void createHospital(Hospital hospital, DateTime hospitalActivationDate) throws JsonProcessingException;
	void addUser(String hospitalId, AmiUser amiUser);
	
	// update master user info
	void updateHospitalMasterUserPwd(String hospitalId ,String userName, String newPwd);
	void updateMasterUserPwd(String hospitalId, String userName, String newPwd) ;
	
	void updateMasterUserEmail(String hospitalId, String userName, String newEmail);
	void updateHospitalMasterUserEmail(String hospitalId, String userName, String newPwd);
	
	void updateMasterUserFirstName(String hospitalId, String userName,String newValue);
	void updateHospitalMasterFirstName(String hospitalId, String userName,String newFirstName);
	
	void updateMasterUserLastName(String hospitalId, String userName,String newValue);
	void updateHospitalMasterLastName(String hospitalId, String userName,String newFirstName);
	
	void updateMasterUserOccupation(String hospitalId, String userName, String newValue);
	void updateHospitalMasterOccupation(String hospitalId, String userName,String newFirstName);
	void switchMasterUser(String hospitalId, String newMasterUser);
	void switchMasterUserService(String hospitalId, String newMasterUser);

}

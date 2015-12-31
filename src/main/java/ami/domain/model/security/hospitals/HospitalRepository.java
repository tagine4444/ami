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
	
	void addUser(String hospitalId, AmiUser amiUser);
	
	// update master user info
	void updateHospitalMasterUserPwd(String hospitalId ,String userName, String newPwd);
	
	void updateHospitalMasterUserEmail(String hospitalId, String userName, String newPwd);
	
	void updateHospitalMasterFirstName(String hospitalId, String userName,String newFirstName);
	
	void updateHospitalMasterLastName(String hospitalId, String userName,String newFirstName);
	
	void updateHospitalMasterOccupation(String hospitalId, String userName,String newFirstName);
	void switchMasterUserService(String hospitalId, String newMasterUser);

	void updateHospitalPhones(String hospitalId, List<Phone> newPhoneList);

	void updateHospitalEmails(String hospitalId, List<Email> newEmailList);

	void updateHospitalAddresses(String hospitalId, List<Address> newAddressList);

	void updateHospitalAcronym(String hospitalId, String newAcronym);

	void updateHospitalNotes(String hospitalId, String newNotes);

	void updateHospitalContract(String hospitalId, String newContract);

	void updateHospitalAccountSize(String hospitalId, String newAccountSize);

}

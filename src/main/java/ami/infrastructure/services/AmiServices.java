package ami.infrastructure.services;

import java.util.List;

import org.joda.time.DateTime;

import ami.domain.model.amicase.Amendment;
import ami.domain.model.security.hospitals.Address;
import ami.domain.model.security.hospitals.Email;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.Phone;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface AmiServices {

	void createHospital(Hospital hospital, DateTime hospitalActivationDate)
			throws JsonProcessingException;

	void updateMasterUserPwd(String hospitalId, String userName, String newPwd);

	void updateMasterUserEmail(String hospitalId, String userName,
			String newEmail);

	void updateMasterUserFirstName(String hospitalId, String userName,
			String newValue);

	void updateMasterUserLastName(String hospitalId, String userName,
			String newValue);

	void updateMasterUserOccupation(String hospitalId, String userName,
			String newValue);

	void switchMasterUser(String hospitalId, String newMasterUser);

	void updateHospitalPhones(String hospitalId, String userName,
			List<Phone> updatedPhoneList);

	void updateHospitalEmails(String hospitalId, String userName,
			List<Email> updatedEmailList);

	void updateHospitalAddresses(String hospitalId, String userName,
			List<Address> updatedEmailList);

	void updateHospitalAcronym(String hospitalId, String userName,
			String newValue);

	void updateHospitalNotes(String hospitalId, String userName, String newValue);
 
	void switchCaseToInProgress(String caseNumber, String userName,
			DateTime dateTime) throws JsonProcessingException;

	void updateHospitalContract(String hospitalId, String userName,
			String newValue);

	void updateHospitalAccountSize(String hospitalId, String userName,
			String newValue);

	void switchCaseToReadyForReview(String caseNumber, String userName,DateTime dateTime);

	void closeCase(String caseNumber, String userName, DateTime dateTime);

	void updateRadiographicInterpretation(String caseNumber, String userName,
			DateTime dateTime, String radiographicInterpretation);

	void updateRadiographicImpression(String caseNumber, String userName,
			DateTime dateTime, String radiographicInterpretation);

	void updateRecommendation(String caseNumber, String userName,
			DateTime dateTime, String radiographicInterpretation);

	void doAccounting(String caseNumber, String userName, DateTime dateTime);

	void amend(String caseNumber, Amendment amendment);

}

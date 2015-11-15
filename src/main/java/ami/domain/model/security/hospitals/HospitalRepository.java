package ami.domain.model.security.hospitals;

import org.joda.time.DateTime;

import ami.domain.model.security.amiusers.AmiUser;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface HospitalRepository {

	
	HospitalView findHospital(String hospitalId) ;
	Hospital findHospitalbyName(String name) ;
	void createHospitalView(Hospital hospital, DateTime hospitalActivationDate) throws JsonProcessingException ;
	void createHospital(Hospital hospital, DateTime hospitalActivationDate)
			throws JsonProcessingException;
	void addUser(String hospitalId, AmiUser amiUser);

}

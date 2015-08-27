package ami.application.services.security;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;

import ami.domain.amiusers.AmiUser;
import ami.domain.hospitals.Hospital;

public interface HospitalService {

	
	Hospital findHospital(String hospitalId) ;
	Hospital findHospitalbyName(String name) ;
	void createHospitalView(Hospital hospital, DateTime hospitalActivationDate) throws JsonProcessingException ;
	void createHospital(Hospital hospital, DateTime hospitalActivationDate)
			throws JsonProcessingException;
	void addUser(String hospitalId, AmiUser amiUser);

}

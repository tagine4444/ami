package ami.application.services.amirequest;

import org.joda.time.DateTime;

import ami.domain.AmiRequestView;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface AmiRequestService {
	
	AmiRequestView findAmiRequest(String requestNumber);
	void createAmiRequest(String amiRequestJson, String userName,   String hospitalName);
	void createAmiRequestView(String amiRequestJson, String userName,   String hospitalName, DateTime time) throws JsonProcessingException;
	void saveAmiRequestAsDraft(String amiRequestJson, String userName,
			String hospitalName);
	

}

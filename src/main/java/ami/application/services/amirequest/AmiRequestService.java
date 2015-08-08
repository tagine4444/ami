package ami.application.services.amirequest;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface AmiRequestService {
	
	void createAmiRequest(String amiRequestJson, String userName,   String hospitalName);
	void createAmiRequestView(String amiRequestJson, String userName,   String hospitalName) throws JsonProcessingException;
	

}

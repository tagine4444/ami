package ami.application.services;

import org.springframework.web.bind.annotation.RequestParam;

import ami.domain.model.amicase.amirequest.AmiRequest;

import com.fasterxml.jackson.core.JsonProcessingException;





public interface AmiRequestService {
	
	
	String findAmiRequest(String requestNumber) throws JsonProcessingException ;
	String findAmiRequestByAnimalName(String animalName) throws JsonProcessingException;
	String findAmiRequestByClientLastName(String clientlastname) throws JsonProcessingException;
	String findAmiRequestBySubmittedDateRange(String date1, String date2) throws JsonProcessingException;
	String findAmiRequestByLast50Records() throws JsonProcessingException;
	String findPendigAmiRequests() throws JsonProcessingException;
	String findDraftAmiRequests() throws JsonProcessingException;
	String getUploadedFiles(@RequestParam String requestNumber) throws JsonProcessingException;
	String submitAmiRequestToRadiologist(String caseNumber,
			AmiRequest amiRequest1, String userName, String hospitalName,
			String hospitalId, String contract, String accountSize);
	String createCaseAsDraft(String caseNumber, AmiRequest req,
			String userName, String hospitalName, String hospitalId,String contract, String accountSize);
	String findPendigAmiRequestsForAllHospitals() throws JsonProcessingException;
		

}

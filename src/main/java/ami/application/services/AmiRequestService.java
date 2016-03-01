package ami.application.services;

import org.springframework.web.bind.annotation.RequestParam;

import ami.domain.model.amicase.amirequest.AmiRequest;

import com.fasterxml.jackson.core.JsonProcessingException;





public interface AmiRequestService {
	
	
	String findAmiRequest(String requestNumber, boolean isAdmin) throws JsonProcessingException ;
	String findAmiRequestByAnimalName(String animalName, boolean isAdmin) throws JsonProcessingException;
	String findAmiRequestByClientLastName(String clientlastname) throws JsonProcessingException;
	String findAmiRequestBySubmittedDateRange(String hospitalId, String date1, String date2) throws JsonProcessingException;
	String findAmiRequestByLast50Records(String hospitalId) throws JsonProcessingException;
	String findAmiRequestByLast50RecordsAdmin(int nRecords) throws JsonProcessingException;
	String findPendigAmiRequests() throws JsonProcessingException;
	String findDraftAmiRequests() throws JsonProcessingException;
	String getUploadedFiles(String requestNumber, boolean isAdmin) throws JsonProcessingException;
	String submitAmiRequestToRadiologist(String caseNumber,
			AmiRequest amiRequest1, String userName, String hospitalName,
			String hospitalId, String contract, String accountSize);
	String createCaseAsDraft(String caseNumber, AmiRequest req,
			String userName, String hospitalName, String hospitalId,String contract, String accountSize);
	String findPendigAmiRequestsForAllHospitals() throws JsonProcessingException;
	String findCaesPendingRevewForAllHospitals() throws JsonProcessingException;
	String findCasesPendingAccounting() throws JsonProcessingException;
	String findAmiAmendments(String caseNumber,boolean isAdmin) throws JsonProcessingException;
	//String findAmiRequestByLast50Records(int nRecords) throws JsonProcessingException;
	void deleteDraftCase(String caseNumber, String hospitalId, String userName);
		

}

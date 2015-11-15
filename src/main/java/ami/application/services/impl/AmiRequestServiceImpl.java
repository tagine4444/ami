package ami.application.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ami.application.services.AmiRequestService;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.amirequest.repo.AmiRequestRepository;
import ami.infrastructure.database.model.AmiRequestView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AmiRequestServiceImpl implements AmiRequestService {
	
	
	private static final Logger log = LoggerFactory.getLogger(AmiRequestServiceImpl.class);
	
	
	
	
	@Autowired
	private AmiRequestRepository amiRequestRepo;
	
	
	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public String findAmiRequest(String requestNumber) throws JsonProcessingException {
		AmiRequestView amiRequestView = amiRequestRepo.findAmiRequest(requestNumber);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findAmiRequestByAnimalName(String animalName) throws JsonProcessingException {
		List<AmiRequestView> amiRequestView = amiRequestRepo.findAmiRequestByAnimalName(animalName);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findAmiRequestByClientLastName(String clientlastname) throws JsonProcessingException{
		List<AmiRequestView> amiRequestView = amiRequestRepo.findAmiRequestByClientLastName(clientlastname);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findAmiRequestBySubmittedDateRange(String date1, String date2) throws JsonProcessingException {
		List<AmiRequestView> amiRequestView = amiRequestRepo.findAmiRequestBySubmittedDateRange( date1,  date2);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findAmiRequestByLast50Records() throws JsonProcessingException{
		final String nRecords = "50";
		List<AmiRequestView> amiRequestView = amiRequestRepo.findAmiRequestByLastNRecords( nRecords);
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findPendigAmiRequests() throws JsonProcessingException{
		List<AmiRequestView> amiRequestView = amiRequestRepo.findPendingAmiRequest();
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String findDraftAmiRequests() throws JsonProcessingException {
		List<AmiRequestView> amiRequestView = amiRequestRepo.findDraftAmiRequest();
		String amiRequestViewString = objectMapper.writeValueAsString(amiRequestView);
		return amiRequestViewString;
	}

	@Override
	public String submitAmiRequestToRadiologist(String caseNumber, AmiRequest amiRequest1 ,String userName ,  String hospitalName, String hospitalId) {
		amiRequestRepo.submitAmiRequestToRadiologist(caseNumber, amiRequest1 ,userName , hospitalName,hospitalId);
		return "{}";
	}

	@Override
	public String createCaseAsDraft(String caseNumber, AmiRequest req ,String userName ,  String hospitalName, String hospitalId) {
		String requestNumber = amiRequestRepo.saveAmiRequestAsDraft(caseNumber,req ,userName , hospitalName, hospitalId ,new DateTime());
		
		return "{\"requestNumber\": \""+requestNumber+"\"}";
	}

	@Override
	public String getUploadedFiles(String requestNumber)
			throws JsonProcessingException {
		AmiRequestView amiRequestView = amiRequestRepo.findAmiRequest( requestNumber);
		List<FileUploadInfo> fileUploads = amiRequestView.getFileUploads();
		
		String fileUploadsString = objectMapper.writeValueAsString(fileUploads);
		return fileUploadsString;
	}

}

package ami.domain.model.amicase.amirequest.repo;

import java.util.List;

import org.joda.time.DateTime;

import ami.domain.model.amicase.Amendment;
import ami.domain.model.amicase.AmendmentNotification;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.infrastructure.database.model.AmiRequestView;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface AmiRequestRepository {
	
	
	AmiRequestView findAmiRequest(String requestNumber, boolean isAdminSearch);
	List<AmiRequestView> findAmiRequestByAnimalName(String animalName,boolean isAdminSearch);
	List<AmiRequestView> findAmiRequestByClientLastName(String clientLastName);
	List<AmiRequestView> findPendingAmiRequest();
	List<AmiRequestView> findDraftAmiRequest();
	List<AmiRequestView> findAmiRequestByLastNRecords(String nRecords, String hospitalId);
	List<AmiRequestView> findAmiRequestByLastNRecordsAdmin(String nRecords);
	
	
	
	void createAmiRequestView( 
			String caseNumber, 
			AmiRequest amiRequestJson, String userName,   String hospitalName , String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime interpretationReadyComplete,           
    		DateTime time,String contract, String accountSize,
    		DateTime accountingDone) throws JsonProcessingException;

	String submitAmiRequestToRadiologist(String caseNumber,AmiRequest amiRequestJson, String userName, String hospitalName, String hospitalId, String contract, String accountSize);
	
	String saveAmiRequestAsDraft(String caseNumber, AmiRequest amiRequestJson, String userName, String hospitalName,String hospitalId,DateTime dateTime, String contract, String accountSize);

	
	void updateUploadedFileList(FileUploadInfo fileUploadInfo, DateTime time)
			throws JsonProcessingException;


	AmiRequestView updateAmiRequestView(String caseNumber, AmiRequest amiRequestJson, String userName,
			String hospitalName, String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist,
			DateTime interpretationInProgress,
			DateTime interpretationReadyForReview,
			DateTime interpretationReadyComplete, boolean editable,
			DateTime time) throws JsonProcessingException;



	List<FileUploadInfo> getUploadedFile(String requestNumber);



	void deleteUploadedFile(String fileName, String requestNumber, DateTime time)
			throws JsonProcessingException;
	List<AmiRequestView> findAmiRequestBySubmittedDateRange(String hospitalId, String date1,
			String date2);
	List<AmiRequestView> findPendigAmiRequestsForAllHospitals(boolean stats);
	void switchCaseToInProgress(DateTime dateTime, String id,String userName);
	void switchCaseToReadyForReview(DateTime dateTime, String id,
			String userName);
	void closeCase(DateTime dateTime, String id, String userName);
	List<AmiRequestView> findCasesPendingReviewForAllHospitals(boolean b);
	void updateRadiographicInterpretation(DateTime dateTime, String id,
			String userName, String radiographicInterpretation);
	void updateRadiographicImpression(DateTime dateTime, String id,
			String userName, String radiographicImpression);
	void updateRecommendation(DateTime dateTime, String id, String userName,
			String recommendation);
	void amendCase(String caseNumber, Amendment amendment);
	void updateAccountingDone(String id, DateTime dateTime, String userName);
	List<AmiRequestView> findCasesPendingAccounting();
	List<AmiRequestView> findAmiRequestByLastNRecords(String nRecords);
	void deleteDraftCase(String caseNumber, String userName, String hospitalId,
			DateTime dateTime);
	void updateDraftCaseToDeleted(String caseNumber, String userName,
			String hospitalId, DateTime dateTime);
	

}

package ami.application.services.amirequest;

import java.util.List;

import org.joda.time.DateTime;

import ami.application.events.amirequest.UploadFileRequestedEvent;
import ami.application.views.AmiRequestView;
import ami.domain.amirequest.AmiRequest;
import ami.domain.amirequest.FileUploadInfo;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface AmiRequestService {
	
	
	
	
	
	
	AmiRequestView findAmiRequest(String requestNumber);
	
	
	
	void createAmiRequestView(AmiRequest amiRequestJson, String userName,   String hospitalName , String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
    		DateTime interpretationInProgress,              
    		DateTime interpretationReadyForReview,          
    		DateTime interpretationReadyComplete,           
    		boolean editable, DateTime time) throws JsonProcessingException;

	void createAmiRequest(AmiRequest amiRequestJson, String userName, String hospitalName, String hospitalId);
	
	String saveAmiRequestAsDraft(AmiRequest amiRequestJson, String userName, String hospitalName,String hospitalId);

	List<AmiRequestView> findPendingAmiRequest();



	void updateUploadedFileList(FileUploadInfo fileUploadInfo, DateTime time)
			throws JsonProcessingException;



	AmiRequestView updateAmiRequestView(AmiRequest amiRequestJson, String userName,
			String hospitalName, String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist,
			DateTime interpretationInProgress,
			DateTime interpretationReadyForReview,
			DateTime interpretationReadyComplete, boolean editable,
			DateTime time) throws JsonProcessingException;

	
	

}

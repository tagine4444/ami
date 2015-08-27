package ami.application.services.amirequest;

import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.CreateAmiRequestCmd;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.services.security.AmiUserService;
import ami.application.services.utils.MongoSequenceService;
import ami.application.views.AmiRequestView;
import ami.application.views.AmiUserView;
import ami.domain.amirequest.AmiRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AmiRequestServiceImpl implements AmiRequestService {
	
	public final static String AMIREQUEST_VIEW = "viewAmirequest";

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private MongoSequenceService mongoSequenceService;
	
	@Autowired
	private MongoTemplate mongo;
	 
	@Autowired
	private AmiUserService amiUserService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public void createAmiRequest(AmiRequest amiRequestJson, String userName,   String hospitalName, String hospitalId) {
		
//		DBObject dbObject = (DBObject) JSON.parse(amiRequestJson);
//		String requestNumber =  (String)dbObject.get("requestNumber");
		
//		if( StringUtils.isEmpty(requestNumber)){
//			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
//			dbObject.put("requestNumber", requestNumber);
//		}
		
		String requestNumber = amiRequestJson.getRequestNumber();
		if( !amiRequestJson.hasRequestNumber()){
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			amiRequestJson.setRequestNumber(requestNumber);
		}
		
		DateTime hasBeenSavedAndSubmittedToRadiologist= null;
		DateTime interpretationInProgress= null;
		DateTime interpretationReadyForReview= null;
		DateTime interpretationReadyComplete= null;
		boolean editable= true;
		
		                              
		commandGateway.sendAndWait(new CreateAmiRequestCmd(requestNumber,amiRequestJson, userName, 
				hospitalName, hospitalId,
				hasBeenSavedAndSubmittedToRadiologist,  interpretationInProgress,
				interpretationReadyForReview, interpretationReadyComplete, editable));
	}
	
	@Override
	public void saveAmiRequestAsDraft(AmiRequest amiRequestJson, String userName,   String hospitalName, String hospitalId) {
		
//		DBObject dbObject = (DBObject) JSON.parse(amiRequestJson);
//		String requestNumber =  (String)dbObject.get("requestNumber");
//		
//		if( StringUtils.isEmpty(requestNumber)){
//			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
//			dbObject.put("requestNumber", requestNumber);
//		}
		
		String requestNumber = amiRequestJson.getRequestNumber();
		if( !amiRequestJson.hasRequestNumber()){
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			amiRequestJson.setRequestNumber(requestNumber);
		}
		
		DateTime hasBeenSavedAndSubmittedToRadiologist= null;
		DateTime interpretationInProgress= null;
		DateTime interpretationReadyForReview= null;
		DateTime interpretationReadyComplete= null;
		boolean editable= true;
		
		commandGateway.sendAndWait(new SaveAmiRequestAsDraftCmd(requestNumber, amiRequestJson, userName, hospitalName,  hospitalId,
				hasBeenSavedAndSubmittedToRadiologist,  interpretationInProgress,
				interpretationReadyForReview, interpretationReadyComplete, editable));
	}


	@Override
	public void createAmiRequestView(AmiRequest amiRequestJson, String userName,
			String hospitalName, String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
			DateTime interpretationInProgress,              
			DateTime interpretationReadyForReview,          
			DateTime interpretationReadyComplete,           
    		boolean editable,
			DateTime time) throws JsonProcessingException {
		
		
		AmiRequestView  view = new AmiRequestView( amiRequestJson,  userName, hospitalName, 
				hospitalid,
				hasBeenSavedAndSubmittedToRadiologist,  
				interpretationInProgress,
				interpretationReadyForReview, 
				interpretationReadyComplete, editable,
				time);
		
		String amiRequestView = objectMapper.writeValueAsString(view);
		
		mongo.save(amiRequestView, AMIREQUEST_VIEW);
		
	}

	@Override
	public AmiRequestView findAmiRequest(String requestNumber) {
		
//		String query = "{ 'amiRequest.requestNumber'  : \"%s\" }"  ;
//		String s = String.format(query, requestNumber);
//		BasicQuery basicQuery = new BasicQuery(s);
//		AmiRequestView amiRequestView1 = mongo.findOne(basicQuery, AmiRequestView.class,"amirequestview");
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("amiRequest.requestNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestView;
	}
	
	@Override
	public List<AmiRequestView> findPendingAmiRequest() {
		
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		AmiUserView userView = amiUserService.findAmiUser(userName);
		String hospitalId = userView.getHospitalId();
		
		
	
	List<AmiRequestView> amiRequestView = mongo.find(Query.query( Criteria.where("hospitalId").is(hospitalId)
															   .andOperator(Criteria.where("editable").is(Boolean.TRUE))
															  )
															  , AmiRequestView.class, AMIREQUEST_VIEW);
		return amiRequestView;
	}

}

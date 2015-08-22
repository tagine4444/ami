package ami.application.services.amirequest;

import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.CreateAmiRequestCmd;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.services.utils.MongoSequenceService;
import ami.application.views.AmiRequestView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
@Service
public class AmiRequestServiceImpl implements AmiRequestService {
	
	public final static String AMIREQUEST_VIEW = "ViewAmirequest";

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private MongoSequenceService mongoSequenceService;
	
	@Autowired
	private MongoTemplate mongo;
	 
	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public void createAmiRequest(String amiRequestJson, String userName,   String hospitalName) {
		
		DBObject dbObject = (DBObject) JSON.parse(amiRequestJson);
		String requestNumber =  (String)dbObject.get("requestNumber");
		
		if( StringUtils.isEmpty(requestNumber)){
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			dbObject.put("requestNumber", requestNumber);
		}
		
		commandGateway.sendAndWait(new CreateAmiRequestCmd(requestNumber, dbObject.toString(), userName, hospitalName));
	}
	
	@Override
	public void saveAmiRequestAsDraft(String amiRequestJson, String userName,   String hospitalName) {
		
		DBObject dbObject = (DBObject) JSON.parse(amiRequestJson);
		String requestNumber =  (String)dbObject.get("requestNumber");
		
		if( StringUtils.isEmpty(requestNumber)){
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			dbObject.put("requestNumber", requestNumber);
		}
		
		commandGateway.sendAndWait(new SaveAmiRequestAsDraftCmd(requestNumber, dbObject.toString(), userName, hospitalName));
	}


	@Override
	public void createAmiRequestView(String amiRequestJson, String userName,
			String hospitalName, DateTime time) throws JsonProcessingException {
		
		AmiRequestView  view = new AmiRequestView( amiRequestJson,  userName, hospitalName, time);
		
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
	
	 

}

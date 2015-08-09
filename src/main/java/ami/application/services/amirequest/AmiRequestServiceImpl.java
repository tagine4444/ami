package ami.application.services.amirequest;

import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ami.application.commands.CreateAmiRequestCmd;
import ami.application.services.utils.MongoSequenceService;
import ami.domain.AmiRequestView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
@Service
public class AmiRequestServiceImpl implements AmiRequestService {

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
	public void createAmiRequestView(String amiRequestJson, String userName,
			String hospitalName) throws JsonProcessingException {
		
		AmiRequestView  view = new AmiRequestView( amiRequestJson,  userName, hospitalName);
		
		String amiRequestView = objectMapper.writeValueAsString(view);
		
		mongo.save(amiRequestView, "amirequestview");
		
	}
	
	 

}

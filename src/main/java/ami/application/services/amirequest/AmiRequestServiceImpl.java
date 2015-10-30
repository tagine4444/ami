package ami.application.services.amirequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.SubmitDraftAmiRequestCmd;
import ami.application.commands.amirequest.SubmitNewAmiRequestCmd;
import ami.application.commands.amirequest.UpdateAmiRequestAsDraftCmd;
import ami.application.services.security.AmiUserService;
import ami.application.services.utils.MongoSequenceService;
import ami.application.views.AmiRequestView;
import ami.application.views.AmiUserView;
import ami.converters.DateTimeToStringConverter;
import ami.domain.amirequest.AmiRequest;
import ami.domain.amirequest.FileUploadInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AmiRequestServiceImpl implements AmiRequestService {
	
	public final static String AMIREQUEST_VIEW = "viewAmirequest";
	public final static String DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss";
	//public final DateTimeFormatter AMI_DATE_FOMRATTER = DateTimeFormat.forPattern(DATE_FORMAT); 

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
	public void submitAmiRequestToRadiologist(AmiRequest amiRequestJson, String userName,   String hospitalName, String hospitalId) {
		
		DateTime hasBeenSavedAndSubmittedToRadiologist = new DateTime();
		
		
		
		String requestNumber = amiRequestJson.getRequestNumber();
		if( !amiRequestJson.hasRequestNumber()){
//			boolean editable = false;
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			amiRequestJson.setRequestNumber(requestNumber);
			
			commandGateway.sendAndWait(new SubmitNewAmiRequestCmd(requestNumber,amiRequestJson, userName, 
					hospitalName, hospitalId,
					hasBeenSavedAndSubmittedToRadiologist
					//, editable
					));
		}else{
			
			boolean editable = false;
			commandGateway.sendAndWait(new SubmitDraftAmiRequestCmd(requestNumber,amiRequestJson, userName, 
					hospitalName, hospitalId,
					hasBeenSavedAndSubmittedToRadiologist, editable, new DateTime()));
			
		}
		                              
		
	}
	
	@Override 
	public String saveAmiRequestAsDraft(AmiRequest amiRequestJson, String userName,   String hospitalName, String hospitalId, DateTime dateTime) {
		
//		DBObject dbObject = (DBObject) JSON.parse(amiRequestJson);
//		String requestNumber =  (String)dbObject.get("requestNumber");
//		
//		if( StringUtils.isEmpty(requestNumber)){
//			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
//			dbObject.put("requestNumber", requestNumber);
//		}
		
		String requestNumber = amiRequestJson.getRequestNumber();
		final boolean isUpdate = amiRequestJson.hasRequestNumber();
		
		
//		DateTime hasBeenSavedAndSubmittedToRadiologist= null;
//		DateTime interpretationInProgress= null;
//		DateTime interpretationReadyForReview= null;
//		DateTime interpretationReadyComplete= null;
//		boolean editable= true;
		
		if( !isUpdate){
			requestNumber = String.valueOf(mongoSequenceService.getNextSequence("counters"));
			amiRequestJson.setRequestNumber(requestNumber);
			
			commandGateway.sendAndWait(new SaveAmiRequestAsDraftCmd(requestNumber, amiRequestJson, userName, hospitalName,  hospitalId
//					,editable
					));
		}else{
			
			commandGateway.sendAndWait(new UpdateAmiRequestAsDraftCmd(requestNumber, amiRequestJson, userName, hospitalName,  hospitalId
//					,editable
					, new DateTime()));
		}
		
		return requestNumber;
	}


	@Override
	public void createAmiRequestView(AmiRequest amiRequestJson, String userName,
			String hospitalName, String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
			DateTime interpretationInProgress,              
			DateTime interpretationReadyForReview,          
			DateTime interpretationReadyComplete,           
//    		boolean editable,
			DateTime time) throws JsonProcessingException {
		
		
		String creationDate = new DateTimeToStringConverter().convert(time);
	    //String creationDate = AMI_DATE_FOMRATTER.print(time);
	    
		boolean editable = hasBeenSavedAndSubmittedToRadiologist==null;
		
		AmiRequestView  view = new AmiRequestView( amiRequestJson,  userName, hospitalName, 
				hospitalid,
				hasBeenSavedAndSubmittedToRadiologist,  
				interpretationInProgress,
				interpretationReadyForReview, 
				interpretationReadyComplete, editable,
				creationDate,
				time,
				new ArrayList<FileUploadInfo>(),
				null,null,time);
		
		String amiRequestView = objectMapper.writeValueAsString(view);
		
		mongo.save(amiRequestView, AMIREQUEST_VIEW);
		
	}
	
	
	@Override
	public AmiRequestView updateAmiRequestView(AmiRequest amiRequest,
			String userName, String hospitalName, String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist,
			DateTime interpretationInProgress,
			DateTime interpretationReadyForReview,
			DateTime interpretationReadyComplete, boolean editableOld,
			DateTime updateDate) throws JsonProcessingException {
		
		
		  
//	    String updateDateString = AMI_DATE_FOMRATTER.print(updateDate);
	    String updateDateString = new DateTimeToStringConverter().convert(updateDate);
	    final String requestNumberJson = "amiRequest.requestNumber";
		
	    boolean editable = hasBeenSavedAndSubmittedToRadiologist ==null;
	    
		Query query = new Query(Criteria.where(requestNumberJson).is(amiRequest.getRequestNumber()));
		Update update = new Update() ;
		update.set("amiRequest", amiRequest);
		update.set("updateDateString", updateDateString);
		update.set("updateDate", updateDate);
		update.set("updateUser", userName);
		update.set("editable", editable);
		
		AmiRequestView updatedView = mongo.findAndModify(query, update, AmiRequestView.class, AMIREQUEST_VIEW); 

		return updatedView;
	}

	@Override
	public AmiRequestView findAmiRequest(String requestNumber) {
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("amiRequest.requestNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestView;
	}
	
	
	@Override
	public List<AmiRequestView> findAmiRequestByAnimalName(String animalName) {
		
		List<AmiRequestView> amiRequestViews = mongo.find(Query.query(Criteria.where("amiRequest.patientInfo.animalName").regex(Pattern.compile(animalName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	@Override
	public List<AmiRequestView> findAmiRequestByClientLastName(String clientLastName) {
		
		List<AmiRequestView>  amiRequestViews = mongo.find(Query.query(Criteria.where("amiRequest.hospitalAndClientInfo.clientLastName").regex(Pattern.compile(clientLastName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	@Override
	public List<AmiRequestView> findAmiRequestBySubmittedDateRange(String date1, String date2) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("hasBeenSavedAndSubmittedToRadiologist").exists(true)
				.andOperator(
					Criteria.where("hasBeenSavedAndSubmittedToRadiologist").gt(date1),
			                Criteria.where("hasBeenSavedAndSubmittedToRadiologist").lt(date2)
				)
			);
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	
	@Override
	public List<AmiRequestView> findAmiRequestByLastNRecords(String nRecords) {
		Query query = new Query();
		query.limit(Integer.parseInt(nRecords));
		query.with(new Sort(Sort.Direction.DESC, "creationDate"));
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	 @Override
	 public void updateUploadedFileList(FileUploadInfo info, @Timestamp DateTime time) throws JsonProcessingException {
	    
		final String requestNumberJson = "amiRequest.requestNumber";
		final String requestNumber = info.getRequestNumber();
		 
		Query query1 = new Query(Criteria.where(requestNumberJson).is(requestNumber));
		Update update = new Update() ;
		update.push("fileUploads", info);
		
		mongo.updateFirst(query1, update, AmiRequestView.class, AMIREQUEST_VIEW); 
	 }
	 
	 @Override
	 public void deleteUploadedFile( String fileName, String requestNumber, @Timestamp DateTime time) throws JsonProcessingException {
		 
		 final String requestNumberJson = "amiRequest.requestNumber";
		 
		 Query query1 = new Query(Criteria.where(requestNumberJson).is(requestNumber));
		 Update update = new Update() ;
		 
		 update.pull("fileUploads", Collections.singletonMap("fileName", fileName));
		 
		 mongo.updateFirst(query1, update, AmiRequestView.class, AMIREQUEST_VIEW); 
		 
	 }
	
	@Override
	public List<AmiRequestView> findPendingAmiRequest() {
		
		
	   String userName = SecurityContextHolder.getContext().getAuthentication().getName();
	   AmiUserView userView = amiUserService.findAmiUser(userName);
	   String hospitalId = userView.getHospitalId();
		
	   Query query =Query.query( Criteria.where("hospitalId").is(hospitalId)
			      .andOperator(Criteria.where("editable").is(Boolean.FALSE)) 
			   );
	   
	   query.with(new Sort(Sort.Direction.DESC, "time"));
	
	   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		return amiRequestView;
	}
	
	@Override
	public List<AmiRequestView> findDraftAmiRequest() {
		 String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		   AmiUserView userView = amiUserService.findAmiUser(userName);
		   String hospitalId = userView.getHospitalId();
			
		   Query query =Query.query( Criteria.where("hospitalId").is(hospitalId)
				      .andOperator(Criteria.where("editable").is(Boolean.TRUE)) 
				   );
		   
		   query.with(new Sort(Sort.Direction.DESC, "time"));
		
		   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
			return amiRequestView;
	}

	@Override
	public List<FileUploadInfo> getUploadedFile(String requestNumber) {
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("amiRequest.requestNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestView.getFileUploads();
	}


}

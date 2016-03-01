package ami.infrastructure.database.mongodb.amicase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ami.application.commands.amirequest.DeleteDraftCaseCmd;
import ami.application.commands.amirequest.SaveAmiRequestAsDraftCmd;
import ami.application.commands.amirequest.SubmitDraftAmiRequestCmd;
import ami.application.commands.amirequest.SubmitNewAmiRequestCmd;
import ami.application.commands.amirequest.UpdateAmiRequestAsDraftCmd;
import ami.domain.model.amicase.Amendment;
import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.amirequest.repo.AmiRequestRepository;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiRequestView;
import ami.infrastructure.database.model.AmiUserView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AmiRequestRepoMongo implements AmiRequestRepository {
	
	public final static String AMENDMENT_NOTIFICATION_VIEW = "viewAmendmentNotification";
	public final static String AMIREQUEST_VIEW = "viewAmirequest";
//	public final static String DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss";
	//public final DateTimeFormatter AMI_DATE_FOMRATTER = DateTimeFormat.forPattern(DATE_FORMAT); 

	@Autowired
	private CommandGateway commandGateway;
	
	@Autowired
	private AmiCaseNumberGeneratorRepository mongoSequenceService;
	
	@Autowired
	private MongoTemplate mongo;
	 
	@Autowired
	private AmiUserRepository amiUserService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	@Override
	public String submitAmiRequestToRadiologist(String caseNumber, AmiRequest amiRequestJson, String userName,   
			String hospitalName, String hospitalId, String contract, String accountSize) {
		
		DateTime hasBeenSavedAndSubmittedToRadiologist = new DateTime();
		
		final boolean isCreate = StringUtils.isEmpty(caseNumber);
		
		if( isCreate ){
			caseNumber = String.valueOf(mongoSequenceService.getNextAmiCase());
			
			commandGateway.sendAndWait(new SubmitNewAmiRequestCmd(caseNumber,amiRequestJson, userName, 
					hospitalName, hospitalId,
					hasBeenSavedAndSubmittedToRadiologist,
					contract,  accountSize
					));
		}else{
			
			commandGateway.sendAndWait(new SubmitDraftAmiRequestCmd(caseNumber,amiRequestJson, userName, 
					hospitalName, hospitalId,
					hasBeenSavedAndSubmittedToRadiologist, 
					new DateTime(),contract,  accountSize));
			
		}
		
		return caseNumber;
		                              
		
	}
	
	@Override 
	public String saveAmiRequestAsDraft(String caseNumber, AmiRequest amiRequestJson, String userName,   
			String hospitalName, String hospitalId, DateTime dateTime, String contract, String accountSize) {
		
		String myCaseNumber = caseNumber;
		
		final boolean isCreate = StringUtils.isEmpty(caseNumber);
		
		if( isCreate){
			myCaseNumber = String.valueOf(mongoSequenceService.getNextAmiCase());
			
			commandGateway.sendAndWait(new SaveAmiRequestAsDraftCmd(myCaseNumber, amiRequestJson, userName, hospitalName,  
					hospitalId,contract,  accountSize
					));
		}else{
			
			commandGateway.sendAndWait(new UpdateAmiRequestAsDraftCmd(myCaseNumber, amiRequestJson, userName, hospitalName,  hospitalId
					, new DateTime(),contract,  accountSize));
		}
		
		return myCaseNumber;
	}
	
	@Override
	public void deleteDraftCase(String caseNumber, String userName,
			String hospitalId, DateTime dateTime) {
		commandGateway.sendAndWait(new DeleteDraftCaseCmd( caseNumber,  userName, hospitalId,  dateTime));
	}


	@Override
	public void createAmiRequestView(String caseNumber, 
			AmiRequest amiRequestJson, 
			String userName,
			String hospitalName, String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
			DateTime interpretationInProgress,              
			DateTime interpretationReadyForReview,           
			DateTime interpretationReadyComplete,           
			DateTime time, 
			String contract, 
			String accountSize,
			DateTime accountingDone) throws JsonProcessingException {
		
		
		AmiRequestView  view = new AmiRequestView( caseNumber, amiRequestJson,  userName, hospitalName, 
				hospitalid,
				hasBeenSavedAndSubmittedToRadiologist,  
				interpretationInProgress,
				interpretationReadyForReview, 
				interpretationReadyComplete, 
				time,
				new ArrayList<FileUploadInfo>(),
				null,time,
				contract,  accountSize,accountingDone);
		
		//String amiRequestView = objectMapper.writeValueAsString(view);
		
		//mongo.save(amiRequestView, AMIREQUEST_VIEW);
		mongo.save(view, AMIREQUEST_VIEW);
		
	}
	
	
	@Override
	public AmiRequestView updateAmiRequestView(String caseNumber ,AmiRequest amiRequest,
			String userName, String hospitalName, String hospitalId,
			DateTime hasBeenSavedAndSubmittedToRadiologist,
			DateTime interpretationInProgress,
			DateTime interpretationReadyForReview,
			DateTime interpretationReadyComplete, boolean editableOld,
			DateTime updateDate) throws JsonProcessingException {
		
		
		  
//	    String updateDateString = AMI_DATE_FOMRATTER.print(updateDate);
//	    String updateDateString = new DateTimeToStringConverter().convert(updateDate);
		
	    boolean editable = hasBeenSavedAndSubmittedToRadiologist ==null;
	    
		Query query = new Query(Criteria.where("caseNumber").is(caseNumber));
		Update update = new Update() ;
		update.set("amiRequest", amiRequest);
//		update.set("updateDateString", updateDateString);
		update.set("updateDate", updateDate);
		update.set("updateUser", userName);
		update.set("editable", editable);
		if( hasBeenSavedAndSubmittedToRadiologist!= null){
			// need this if statement cause mongo throws a null pointer exception if you try to set
			// hasBeenSavedAndSubmittedToRadiologist with a null value
			update.set("hasBeenSavedAndSubmittedToRadiologist", hasBeenSavedAndSubmittedToRadiologist);
//			update.set("hasBeenSavedAndSubmittedToRadiologistString", hasBeenSavedAndSubmittedToRadiologist);
		}
		
		AmiRequestView updatedView = mongo.findAndModify(query, update, AmiRequestView.class, AMIREQUEST_VIEW); 

		return updatedView;
	}

	@Override
	public AmiRequestView findAmiRequest(String requestNumber,boolean isAdminSearch) {
		AmiRequestView amiRequestView = null;
		if(!isAdminSearch){
			
			String userName =SecurityContextHolder.getContext().getAuthentication().getName();
			AmiUserView userView = amiUserService.findAmiUser(userName);
			String hospitalId = userView.getHospitalId();
			Query query = new Query();
			
			query.addCriteria(Criteria.where("caseNumber").is(requestNumber)
					.andOperator(Criteria.where("hospitalId").is(hospitalId),Criteria.where("draftCaseDeletedTime").is(null)));
			
			 amiRequestView = mongo.findOne(query, AmiRequestView.class,AMIREQUEST_VIEW);
		}else{
			 amiRequestView = mongo.findOne(Query.query(Criteria.where("caseNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		}
		
		return amiRequestView;
	}
	
	
	@Override
	public List<AmiRequestView> findAmiRequestByAnimalName(String animalName, boolean isAdminSearch) {
		
		List<AmiRequestView> amiRequestViews =null;
		if(!isAdminSearch){
			
			String userName =SecurityContextHolder.getContext().getAuthentication().getName();
			AmiUserView userView = amiUserService.findAmiUser(userName);
			String hospitalId = userView.getHospitalId();
			Query query = new Query();
			
			query.addCriteria(Criteria.where("amiRequest.patientInfo.animalName").regex(Pattern.compile(animalName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
					.andOperator(Criteria.where("hospitalId").is(hospitalId), Criteria.where("draftCaseDeletedTime").is(null)));
			
			amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
			 
			 
		}
		else{
			 amiRequestViews = mongo.find(Query.query(
					 Criteria.where("amiRequest.patientInfo.animalName").regex(Pattern.compile(animalName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
					 .andOperator(Criteria.where("draftCaseDeletedTime").is(null))), AmiRequestView.class,AMIREQUEST_VIEW);
		}
		
		
		return amiRequestViews;
	}
	
	@Override
	public List<AmiRequestView> findAmiRequestByClientLastName(String clientLastName) {
		
		List<AmiRequestView>  amiRequestViews = mongo.find(Query.query(
				Criteria.where("amiRequest.hospitalAndClientInfo.clientLastName").regex(Pattern.compile(clientLastName, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE))
				.andOperator(Criteria.where("draftCaseDeletedTime").is(null))), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	@Override
	public List<AmiRequestView> findAmiRequestBySubmittedDateRange(String hospitalId, String date1, String date2) {
		
	
		
//		int firstSlah  = date1.indexOf("/");
//		int secondSlah = date1.lastIndexOf("/");
//		
//		int monthOfYear1 = Integer.valueOf(date1.substring(0, firstSlah)); 
//		int dayOfMonth1  = Integer.valueOf(date1.substring(firstSlah +1, secondSlah)); 
//		int year1 = Integer.valueOf(date1.substring(secondSlah+1, date1.length())); 
//		
//		firstSlah  = date2.indexOf("/");
//		 secondSlah = date2.lastIndexOf("/");
//		
//		int monthOfYear2 = Integer.valueOf(date2.substring(0, firstSlah)); 
//		int dayOfMonth2  = Integer.valueOf(date2.substring(firstSlah +1, secondSlah)); 
//		int year2 = Integer.valueOf(date2.substring(secondSlah+1, date2.length())); 
//		
//		DateTime aDate1 = new DateTime(year1, monthOfYear1, dayOfMonth1, 0, 0, DateTimeZone.forID("America/Los_Angeles"));
//		DateTime aDate2 = new DateTime(year2, monthOfYear2, dayOfMonth2, 23, 59, DateTimeZone.forID("America/Los_Angeles"));
//		
		
		
		DateTimeFormatter dateFormatter =  DateTimeFormat.forPattern("EEE MMM dd yyyy HH:mm:ss");
		
		
//		Wed Feb 03 2016 00:00:00 GMT-0800 (PST)
		
		DateTime aDate11 = dateFormatter.parseDateTime( date1.substring(0, date1.indexOf("GMT") ).trim());
		DateTime aDate22 = dateFormatter.parseDateTime( date2.substring(0, date2.indexOf("GMT") ).trim());
		
		
		DateTime aDate1 = new DateTime(aDate11.getYear(), aDate11.getMonthOfYear(), aDate11.getDayOfMonth(), 0, 0, DateTimeZone.forID("America/Los_Angeles"));
		DateTime aDate2 = new DateTime(aDate22.getYear(), aDate22.getMonthOfYear(), aDate22.getDayOfMonth(), 23, 59, DateTimeZone.forID("America/Los_Angeles"));
//		
		
		
		
		Query query = new Query();
		
		query.addCriteria(
				Criteria.where("hasBeenSavedAndSubmittedToRadiologist").exists(true)
						.andOperator(
									 Criteria.where("hasBeenSavedAndSubmittedToRadiologist").gte(aDate1),
					                 Criteria.where("hasBeenSavedAndSubmittedToRadiologist").lte(aDate2),
					                 Criteria.where("draftCaseDeletedTime").is(null)
					    
				)
			);
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	
	@Override
	public List<AmiRequestView> findAmiRequestByLastNRecordsAdmin(String nRecords) {
		Query query = new Query();
		query.addCriteria(Criteria.where("draftCaseDeletedTime").is(null));
		query.limit(Integer.parseInt(nRecords));
		query.with(new Sort(Sort.Direction.ASC, "caseNumber"));
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	@Override
	public List<AmiRequestView> findAmiRequestByLastNRecords(String nRecords, String hospitalId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("draftCaseDeletedTime").is(null));
		query.limit(Integer.parseInt(nRecords));
		query.with(new Sort(Sort.Direction.ASC, "caseNumber"));
		
		query.addCriteria(
				Criteria.where("hospitalId").is(hospitalId)
			);
		
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	@Override
	public List<AmiRequestView> findAmiRequestByLastNRecords(String nRecords) {
		Query query = new Query();
		query.addCriteria(Criteria.where("draftCaseDeletedTime").is(null));
		query.limit(Integer.parseInt(nRecords));
		query.with(new Sort(Sort.Direction.ASC, "caseNumber"));
		
		List<AmiRequestView>  amiRequestViews = mongo.find(query, AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestViews;
	}
	
	 @Override
	 public void updateUploadedFileList(FileUploadInfo info, @Timestamp DateTime time) throws JsonProcessingException {
	    
		final String requestNumberJson = "caseNumber";
		final String requestNumber = info.getRequestNumber();
		 
		Query query1 = new Query(Criteria.where(requestNumberJson).is(requestNumber));
		Update update = new Update() ;
		update.push("fileUploads", info);
		
		mongo.updateFirst(query1, update, AmiRequestView.class, AMIREQUEST_VIEW); 
	 }
	 
	 @Override
	 public void deleteUploadedFile( String fileName, String requestNumber, @Timestamp DateTime time) throws JsonProcessingException {
		 
		 final String requestNumberJson = "caseNumber";
		 
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
			      						.andOperator(Criteria.where("editable").is(Boolean.FALSE) ,Criteria.where("caseClosed").is(null),
			      									Criteria.where("draftCaseDeletedTime").is(null)
			    		  ) 
			   );
	   
	   query.with(new Sort(Sort.Direction.DESC, "hasBeenSavedAndSubmittedToRadiologist"));
	
	   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		return amiRequestView;
	}
	
	
	@Override
	public List<AmiRequestView> findPendigAmiRequestsForAllHospitals(boolean stats) {
		
		   Query query =Query.query( Criteria.where("editable").is(Boolean.FALSE)
				   .andOperator(Criteria.where("amiRequest.requestedServices.isStat").is(stats),
						   Criteria.where("caseClosed").is(null),
						   Criteria.where("interpretationReadyForReview").is(null),
						   Criteria.where("draftCaseDeletedTime").is(null))
						   
				   );
		   query.with(new Sort(Sort.Direction.ASC, "hasBeenSavedAndSubmittedToRadiologist"));
		   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		   return amiRequestView;
	}
	
	@Override
	public List<AmiRequestView> findDraftAmiRequest() {
		 String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		   AmiUserView userView = amiUserService.findAmiUser(userName);
		   String hospitalId = userView.getHospitalId();
			
		   Query query =Query.query( Criteria.where("hospitalId").is(hospitalId)
				      .andOperator(Criteria.where("editable").is(Boolean.TRUE), Criteria.where("draftCaseDeletedTime").is(null)) 
				   );
		   
		   query.with(new Sort(Sort.Direction.DESC, "creationDate"));
		
		   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
			return amiRequestView;
	}

	@Override
	public List<FileUploadInfo> getUploadedFile(String requestNumber) {
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("caseNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestView.getFileUploads();
	}

	@Override
	public void switchCaseToInProgress(DateTime dateTime, String caseNumber, String userName) {
	
			mongo.updateFirst(
		            new Query(Criteria.where("caseNumber").is(caseNumber)),
		            Update.update("interpretationInProgress", dateTime),
		            AMIREQUEST_VIEW);
		
	}

	@Override
	public void switchCaseToReadyForReview(DateTime dateTime, String caseNumber,
			String userName) {

		Update update = new Update();
		update.set("interpretationReadyForReview", dateTime);
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
		
	}

	@Override
	public void closeCase(DateTime dateTime, String caseNumber, String userName) {

		Update update = new Update();
		update.set("caseClosed", dateTime);
//		update.set("caseClosedString", dateTime.toString());
		update.set("editable", Boolean.FALSE);

		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
		
	}

	@Override
	public List<AmiRequestView> findCasesPendingReviewForAllHospitals(boolean stats) {
		Query query =Query.query( Criteria.where("caseClosed").is(null)
				   .andOperator(Criteria.where("amiRequest.requestedServices.isStat").is(stats),
						   Criteria.where("interpretationReadyForReview").ne(null),
						   Criteria.where("draftCaseDeletedTime").is(null))
				   );
		   query.with(new Sort(Sort.Direction.ASC, "time"));
		   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		   return amiRequestView;
	}

	@Override
	public void updateRadiographicInterpretation(DateTime dateTime, String caseNumber,
			String userName, String radiographicInterpretation) {
		Update update = new Update();
		update.set("radiographicInterpretation", radiographicInterpretation);
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
		
	}

	@Override
	public void updateRadiographicImpression(DateTime dateTime, String caseNumber,
			String userName, String radiographicImpression) {
		Update update = new Update();
		update.set("radiographicImpression",radiographicImpression );
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
	}

	@Override
	public void updateRecommendation(DateTime dateTime, String caseNumber,
			String userName, String recommendation) {
		
		Update update = new Update();
		update.set("recommendation", recommendation );
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
	}

	@Override
	public void amendCase( String caseNumber, Amendment amendment) {
		 
		Query query1 = new Query(Criteria.where("caseNumber").is(caseNumber));
		Update update = new Update() ;
		update.push("amendments", amendment);
		
		mongo.updateFirst(query1, update, AmiRequestView.class, AMIREQUEST_VIEW); 
	}

	@Override
	public void updateAccountingDone(String caseNumber, DateTime dateTime,
			String userName) {
		Update update = new Update();
		update.set("accountingDone", dateTime);
//		update.set("accountingDoneString", dateTime.toString());
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber)),
	            update,
	            AMIREQUEST_VIEW);
	}

	@Override
	public List<AmiRequestView> findCasesPendingAccounting() {
		
		  Query query = Query.query( Criteria.where("accountingDone").is(null)
				   .andOperator( Criteria.where("caseClosed").ne(null)) );
		   query.with(new Sort(Sort.Direction.ASC, "time"));
		   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		   return amiRequestView;
	}

	
	@Override
	public void updateDraftCaseToDeleted(String caseNumber, String userName, String hospitalId, DateTime dateTime) {
		Update update = new Update();
		update.set("draftCaseDeletedTime", dateTime);
		update.set("draftCaseDeletedBy", userName);
		
		mongo.updateFirst(
	            new Query(Criteria.where("caseNumber").is(caseNumber).andOperator( Criteria.where("hospitalId").is(hospitalId))),
	            update,
	            AMIREQUEST_VIEW);
	}


}

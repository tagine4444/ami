package ami.infrastructure.database.mongodb.amicase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;
import ami.domain.model.amicase.amirequest.AmiRequest;
import ami.domain.model.amicase.amirequest.FileUploadInfo;
import ami.domain.model.amicase.amirequest.repo.AmiRequestRepository;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiRequestView;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.database.model.HospitalView;
import ami.web.converters.DateTimeToStringConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class AmiRequestRepoMongo implements AmiRequestRepository {
	
	public final static String AMIREQUEST_VIEW = "viewAmirequest";
	public final static String DATE_FORMAT     = "yyyy-MM-dd HH:mm:ss";
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
	public void submitAmiRequestToRadiologist(String caseNumber, AmiRequest amiRequestJson, String userName,   
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
	public void createAmiRequestView(String caseNumber, AmiRequest amiRequestJson, String userName,
			String hospitalName, String hospitalid, 
			DateTime hasBeenSavedAndSubmittedToRadiologist, 
			DateTime interpretationInProgress,              
			DateTime interpretationReadyForReview,           
			DateTime interpretationReadyComplete,           
			DateTime time, String contract, String accountSize) throws JsonProcessingException {
		
		
		String creationDate = new DateTimeToStringConverter().convert(time);
	    //String creationDate = AMI_DATE_FOMRATTER.print(time);
	    
		boolean editable = hasBeenSavedAndSubmittedToRadiologist==null;
		
		AmiRequestView  view = new AmiRequestView( caseNumber, amiRequestJson,  userName, hospitalName, 
				hospitalid,
				hasBeenSavedAndSubmittedToRadiologist,  
				interpretationInProgress,
				interpretationReadyForReview, 
				interpretationReadyComplete, editable,
				creationDate,
				time,
				new ArrayList<FileUploadInfo>(),
				null,null,time,
				contract,  accountSize);
		
		String amiRequestView = objectMapper.writeValueAsString(view);
		
		mongo.save(amiRequestView, AMIREQUEST_VIEW);
		
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
	    String updateDateString = new DateTimeToStringConverter().convert(updateDate);
	    final String requestNumberJson = "caseNumber";
		
	    boolean editable = hasBeenSavedAndSubmittedToRadiologist ==null;
	    
		Query query = new Query(Criteria.where(requestNumberJson).is(caseNumber));
		Update update = new Update() ;
		update.set("amiRequest", amiRequest);
		update.set("updateDateString", updateDateString);
		update.set("updateDate", updateDate);
		update.set("updateUser", userName);
		update.set("editable", editable);
		update.set("hasBeenSavedAndSubmittedToRadiologist", hasBeenSavedAndSubmittedToRadiologist);
		
		AmiRequestView updatedView = mongo.findAndModify(query, update, AmiRequestView.class, AMIREQUEST_VIEW); 

		return updatedView;
	}

	@Override
	public AmiRequestView findAmiRequest(String requestNumber) {
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("caseNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
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
			      .andOperator(Criteria.where("editable").is(Boolean.FALSE)) 
			   );
	   
	   query.with(new Sort(Sort.Direction.DESC, "time"));
	
	   List<AmiRequestView> amiRequestView = mongo.find(query,AmiRequestView.class, AMIREQUEST_VIEW);
		return amiRequestView;
	}
	
	
	@Override
	public List<AmiRequestView> findPendigAmiRequestsForAllHospitals(boolean stats) {
		
		   Query query =Query.query( Criteria.where("editable").is(Boolean.FALSE)
				   .andOperator(Criteria.where("amiRequest.requestedServices.isStat").is(stats)));
		   query.with(new Sort(Sort.Direction.ASC, "time"));
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
		
		AmiRequestView amiRequestView = mongo.findOne(Query.query(Criteria.where("caseNumber").is(requestNumber)), AmiRequestView.class,AMIREQUEST_VIEW);
		return amiRequestView.getFileUploads();
	}

	@Override
	public void switchCaseToInProgress(DateTime dateTime, String caseNumber) {
	
			mongo.updateFirst(
		            new Query(Criteria.where("caseNumber").is(caseNumber)),
		            Update.update("interpretationInProgress", dateTime),
		            AMIREQUEST_VIEW);
		
	}

	


}

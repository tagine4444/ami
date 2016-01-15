package ami.infrastructure.database.mongodb.security;

import java.util.Iterator;
import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.application.commands.security.CreateAmiUserCmd;
import ami.application.commands.security.UpdateMasterUserPwdCmd;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiUserView;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonProcessingException;
@Service
public class AmiUserRepoMongo implements AmiUserRepository{
	
	private final static String AMI_USER_VIEW = "viewSecAmiuser";
	
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private CommandGateway commandGateway;
	

	@Override
	public AmiUserView findAmiUser(String userName)  {
		
		AmiUserView  view = mongo.findOne(Query.query(Criteria.where("amiUser.user").is(userName)), AmiUserView.class,AMI_USER_VIEW);
		
		if(view==null){
			throw new RuntimeException("User not found");
		}
		
		view.getAmiUser().blurPassword();
		return view;
	}
	
	@Override
	public AmiUserView authenticate(String userName, String pwd) {
		
		Query query = new Query();
		query.addCriteria(
				Criteria.where("amiUser.user").is(userName)
				.andOperator(
					Criteria.where("amiUser.pwd").is(pwd)
				)
			);
		
		AmiUserView  view = mongo.findOne(query, AmiUserView.class,AMI_USER_VIEW);
		if(view==null){
			throw new RuntimeException("User not found");
		}
		return view;
	}
	
	@Override
	public void createAmiUser(String hospitalId, String hospitalName, AmiUser amiUser) throws JsonProcessingException {
		commandGateway.sendAndWait(new CreateAmiUserCmd( hospitalId, hospitalName, amiUser));
	}
	
	@Override
	public void updateMasterUserPwd(String hospitalId, String userName, String newPwd) throws JsonProcessingException {
		
		
		commandGateway.sendAndWait(new UpdateMasterUserPwdCmd( hospitalId, userName, newPwd));
	}

	
	@Override
	public void createAmiUserView(String hospitalId, String hospitalName, AmiUser amiUser) throws JsonProcessingException {
		
		AmiUserView  view =
				new AmiUserView( amiUser,hospitalId,  hospitalName , new DateTime());
		
		mongo.save(view, AMI_USER_VIEW);
		
	}

	@Override
	public void upateMasterUserPwd(String userName, String newPwd) {
		
		mongo.updateFirst(
				new Query(Criteria.where("amiUser.user").is(userName)),
				Update.update("amiUser.pwd", newPwd),
				AMI_USER_VIEW);
		
		
	}

	@Override
	public void upateMasterUserEmail(String userName, String newEmail) {
		
		mongo.updateFirst(
				new Query(Criteria.where("amiUser.user").is(userName)),
				Update.update("amiUser.email", newEmail),
				AMI_USER_VIEW);
		
	}

	@Override
	public void upateMasterUserFirstName(String userName, String newFirstName) {
		mongo.updateFirst(
				new Query(Criteria.where("amiUser.user").is(userName)),
				Update.update("amiUser.firstName", newFirstName),
				AMI_USER_VIEW);
		
	}

	@Override
	public void upateMasterUserLastName(String userName, String newLastName) {
		
		mongo.updateFirst(
				new Query(Criteria.where("amiUser.user").is(userName)),
				Update.update("amiUser.lastName", newLastName),
				AMI_USER_VIEW);
		
	}

	@Override
	public void upateMasterUserOccupation(String userName, String newOccupation) {
		mongo.updateFirst(
				new Query(Criteria.where("amiUser.user").is(userName)),
				Update.update("amiUser.occupation", newOccupation),
				AMI_USER_VIEW);
		
	}

	@Override
	public void switchMasterUserService(String hospitalId, String newMasterUser) {
		
		List<AmiUserView>  views = mongo.find(Query.query(Criteria.where("hospitalId").is(hospitalId)), AmiUserView.class,AMI_USER_VIEW);
		Iterator<AmiUserView> it = views.iterator();
		while(it.hasNext()){
			AmiUserView anAmiUserView = it.next();
			AmiUser amiUser = anAmiUserView.getAmiUser();
			
			if(amiUser.getUser().equals(newMasterUser)){
				amiUser.addMasterUserRole();
				mongo.updateFirst(
						new Query(Criteria.where("hospitalId").is(hospitalId).and("amiUser.user").is(amiUser.getUser())),
						Update.update("amiUser", amiUser),
						AMI_USER_VIEW);
				
			}else{
				if(amiUser.isMasterUser()){
					amiUser.removeMasterUserRole();
					mongo.updateFirst(
							new Query(Criteria.where("hospitalId").is(hospitalId).and("amiUser.user").is(amiUser.getUser())),
							Update.update("amiUser", amiUser),
							AMI_USER_VIEW);
				}
				
			}
			
		}
		
		;
		
	}


}

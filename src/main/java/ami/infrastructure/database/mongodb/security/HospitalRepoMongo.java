package ami.infrastructure.database.mongodb.security;

import java.util.Iterator;
import java.util.List;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.application.commands.security.CreateHospitalCmd;
import ami.application.commands.security.SwitchMasterUserCmd;
import ami.application.commands.security.UpdateMasterUserEmailCmd;
import ami.application.commands.security.UpdateMasterUserFirstNameCmd;
import ami.application.commands.security.UpdateMasterUserLastNameCmd;
import ami.application.commands.security.UpdateMasterUserOccupationCmd;
import ami.application.commands.security.UpdateMasterUserPwdCmd;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.infrastructure.database.model.HospitalView;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class HospitalRepoMongo implements HospitalRepository{
	
	private final static String AMI_HOSPITAL_VIEW = "viewSecHospital";
	
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private CommandGateway commandGateway;
	 
	
	@Override
	public void createHospital(Hospital hospital ,DateTime hospitalActivationDate) throws JsonProcessingException {
		
		commandGateway.sendAndWait(new CreateHospitalCmd(hospital,hospitalActivationDate));
	}
	@Override
	public void createHospitalView(Hospital hospital,
			@Timestamp DateTime hospitalActivationDate) throws JsonProcessingException {
		
		HospitalView  view =
				new HospitalView( hospital, hospitalActivationDate,null, null,null);
		
		mongo.save(view, AMI_HOSPITAL_VIEW);
	}
	@Override
	public HospitalView findHospital(String hospitalId) {
		
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital._id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		return view;
		
	}
	@Override
	public void addUser(String hospitalId, AmiUser amiUser) {
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital._id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		view.addUser(amiUser);
		
		mongo.updateFirst(
	            new Query(Criteria.where("hospital._id").is(hospitalId)),
	            Update.update("amiUsers", view.getAmiUsers()),
	            AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public Hospital findHospitalbyName(String name) {

		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.name").is(name)), HospitalView.class,AMI_HOSPITAL_VIEW);
		return view.getHospital();
	}
	@Override
	public List<HospitalView> getAllHospitals() {
		List<HospitalView>  views = mongo.findAll( HospitalView.class,AMI_HOSPITAL_VIEW);
		return views;
	}
	
	
	
	@Override
	public void updateMasterUserPwd(String hospitalId ,String userName, String newPwd) {
		commandGateway.sendAndWait(new UpdateMasterUserPwdCmd( hospitalId , userName, newPwd));
	}
	
	@Override
	public void updateHospitalMasterUserPwd(String hospitalId ,String userName, String newPwd) {
		
		
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(userName)){
				anAmiUser.updatePwd(newPwd);
				break;
			}
		}
		
		mongo.updateFirst(
	            new Query(Criteria.where("hospital._id").is(hospitalId)),
	            Update.update("amiUsers", amiUsers),
	            AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public void updateHospitalMasterUserEmail(String hospitalId ,String userName, String newEmail) {
		
		
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(userName)){
				anAmiUser.updateEmail(newEmail);
				break;
			}
		}
		
		mongo.updateFirst(
				new Query(Criteria.where("hospital._id").is(hospitalId)),
				Update.update("amiUsers", amiUsers),
				AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public void updateMasterUserEmail(String hospitalId, String userName,
			String newEmail)  {
		commandGateway.sendAndWait(new UpdateMasterUserEmailCmd( hospitalId , userName, newEmail));
		
	}
	@Override
	public void updateMasterUserFirstName(String hospitalId, String userName,
			String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserFirstNameCmd( hospitalId , userName, newValue));
		
	}
	
	@Override
	public void updateHospitalMasterFirstName(String hospitalId, String userName, String newFirstName) {
		
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(userName)){
				anAmiUser.updateFirstName(newFirstName);
				break;
			}
		}
		
		mongo.updateFirst(
				new Query(Criteria.where("hospital._id").is(hospitalId)),
				Update.update("amiUsers", amiUsers),
				AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public void updateMasterUserLastName(String hospitalId, String userName, String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserLastNameCmd( hospitalId , userName, newValue));
		
	}
	@Override
	public void updateHospitalMasterLastName(String hospitalId,
			String userName, String newLastName) {
		
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(userName)){
				anAmiUser.updateLastName(newLastName);
				break;
			}
		}
		
		mongo.updateFirst(
				new Query(Criteria.where("hospital._id").is(hospitalId)),
				Update.update("amiUsers", amiUsers),
				AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public void updateMasterUserOccupation(String hospitalId, String userName, String newValue) {
		commandGateway.sendAndWait(new UpdateMasterUserOccupationCmd( hospitalId , userName, newValue));
		
	}
	@Override
	public void updateHospitalMasterOccupation(String hospitalId,
			String userName, String newOccupation) {
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(userName)){
				anAmiUser.updateOccupation(newOccupation);
				break;
			}
		}
		
		mongo.updateFirst(
				new Query(Criteria.where("hospital._id").is(hospitalId)),
				Update.update("amiUsers", amiUsers),
				AMI_HOSPITAL_VIEW);
		
	}
	@Override
	public void switchMasterUser(String hospitalId, String newMasterUser) {
		commandGateway.sendAndWait(new SwitchMasterUserCmd( hospitalId , newMasterUser));
	}
	
	@Override
	public void switchMasterUserService(String hospitalId, String newMasterUser) {
		HospitalView  view = mongo.findOne(Query.query(Criteria.where("hospital.id").is(hospitalId)), HospitalView.class,AMI_HOSPITAL_VIEW);
		List<AmiUser> amiUsers = view.getAmiUsers();
		Iterator<AmiUser> it = amiUsers.iterator();
		while(it.hasNext()){
			
			AmiUser anAmiUser = it.next();
			if(anAmiUser.getUser().equals(newMasterUser)){
				anAmiUser.addMasterUserRole();
			}else{
				anAmiUser.removeMasterUserRole();
			}
		}
		
		mongo.updateFirst(
				new Query(Criteria.where("hospital._id").is(hospitalId)),
				Update.update("amiUsers", amiUsers),
				AMI_HOSPITAL_VIEW);
		
	}

}

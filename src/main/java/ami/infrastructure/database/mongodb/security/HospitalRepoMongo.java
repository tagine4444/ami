package ami.infrastructure.database.mongodb.security;

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

}

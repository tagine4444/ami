package ami.infrastructure.database.mongodb.security;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import ami.application.commands.security.CreateAmiUserCmd;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.infrastructure.database.model.AmiUserView;

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
		return view;
	}
	
	@Override
	public void createAmiUser(String hospitalId, String hospitalName, AmiUser amiUser) throws JsonProcessingException {
		commandGateway.sendAndWait(new CreateAmiUserCmd( hospitalId, hospitalName, amiUser));
	}

	@Override
	public void createAmiUserView(String hospitalId, String hospitalName, AmiUser amiUser) throws JsonProcessingException {
		
		AmiUserView  view =
				new AmiUserView( amiUser,hospitalId,  hospitalName , new DateTime());
		
		mongo.save(view, AMI_USER_VIEW);
		
	}

}

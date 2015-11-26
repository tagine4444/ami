package ami.infrastructure.database.mongodb.amicase;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.AmiCaseNumberGeneratorRepository;

@Service
public class AmiCaseNumberGeneratorRepoMongo implements AmiCaseNumberGeneratorRepository {

	@Autowired
	private MongoTemplate mongo;

	@Override
	public int getNextAmiCase() {
		Counter counter = mongo.findAndModify(
				query(where("_id").is("amirequestsequence")),
				new Update().inc("seq", 1), options().returnNew(true),
				Counter.class);

		return counter.getSeq();
	}
	@Override
	public int getNextHospitalId() {
		Counter counter = mongo.findAndModify(
				query(where("_id").is("amihospitalsequence")),
				new Update().inc("seq", 1), options().returnNew(true),
				Counter.class);
		
		return counter.getSeq();
	}
}

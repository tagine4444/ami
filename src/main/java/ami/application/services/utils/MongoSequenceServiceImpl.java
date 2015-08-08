package ami.application.services.utils;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import ami.infrastructure.database.mongodb.Counter;

@Service
public class MongoSequenceServiceImpl implements MongoSequenceService {

	@Autowired
	private MongoTemplate mongo;

	@Override
	public int getNextSequence(String collectionName) {
		Counter counter = mongo.findAndModify(
				query(where("_id").is("amirequestsequence")),
				new Update().inc("seq", 1), options().returnNew(true),
				Counter.class);

		return counter.getSeq();
	}
}

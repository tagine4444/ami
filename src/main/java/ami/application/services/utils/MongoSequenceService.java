package ami.application.services.utils;

public interface MongoSequenceService {

	int getNextSequence(String collectionName);

}

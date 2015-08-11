package ami.application.services.animals;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ami.domain.referencedata.Animals;

@Service
public class AnimalServiceImpl implements AnimalService {

	@Autowired
	private MongoTemplate mongo;

	@Autowired
	private Environment env;
	

	@Cacheable("animals")
	public List<Animals> getAnimals() {
		List<Animals> animals = mongo.findAll(Animals.class);
		return animals;
	}
	
	@Override
	@CachePut(value = "animals")
	public Animals addAnimals(Animals animal){
		mongo.save(animal, "animals");
		return animal;
		
	}

}

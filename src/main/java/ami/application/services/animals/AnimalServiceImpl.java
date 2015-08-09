package ami.application.services.animals;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void addAnimals(){
		
		String canines       = (String)env.getProperty("Canine");
		String[] canineArray = canines.split(",");
		List<String> canineBreedList  =  Arrays.asList(canineArray);
		Animals canine = new Animals("Canine", canineBreedList);
		
		
		
		String felines       = (String)env.getProperty("Feline");
		String[] felineArray = felines.split(",");
		List<String> felineBreedList  =  Arrays.asList(felineArray);
		Animals feline = new Animals("Feline", felineBreedList);
		
		
		mongo.save(canine, "animals");
		mongo.save(feline, "animals");

	}

	

}

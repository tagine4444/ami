package ami.infrastructure.database.mongodb.amicase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.AnimalRepository;
import ami.domain.model.amicase.Animals;

@Service
public class AnimalRepositoryMongo implements AnimalRepository {
	
	public final static String REFDATA_ANIMAL = "refdataAnimals";
	
	@Autowired
	private MongoTemplate mongo;

	@Autowired
	private Environment env;
	

	@Override
	@Cacheable("amicache")
	public List<Animals> getAnimals() {
		List<Animals> animals = mongo.findAll(Animals.class,REFDATA_ANIMAL);
		return animals;
	}
	
	@Override
	public List<String> getSpecies() {
        
		List<Animals> animals = mongo.findAll(Animals.class,REFDATA_ANIMAL);
		
		List<String> species = new ArrayList<String>(animals.size());
		for (Animals animals2 : animals) {
			species.add(animals2.getName());
		}
		return species;
	}
	
	@Override
	@CachePut(value = "amicache")
	public Animals addAnimals(Animals animal){
		mongo.save(animal, REFDATA_ANIMAL);
		return animal;
		
	}

}

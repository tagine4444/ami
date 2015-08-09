package ami.application.services.animals;

import java.util.List;

import ami.domain.referencedata.Animals;


public interface AnimalService {

//	List<String>  getBreeds(String breed);
	List<Animals> getAnimals() ;

	void addAnimals();
}

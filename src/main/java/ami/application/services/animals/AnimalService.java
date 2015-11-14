package ami.application.services.animals;

import java.util.List;

import ami.domain.model.amicase.Animals;


public interface AnimalService {

	List<Animals> getAnimals() ;

	Animals addAnimals(Animals animal);

	List<String> getSpecies();
}

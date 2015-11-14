package ami.domain.model.amicase;

import java.util.List;


public interface AnimalRepository {

	List<Animals> getAnimals() ;

	Animals addAnimals(Animals animal);

	List<String> getSpecies();
}

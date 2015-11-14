package ami.domain.amicase.amirequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PatientInfo {
	
	private String animalName;
	private String animalSex;
	private String animalWeight;
	private String animalWeightUom;
	private String animalAgeYears;
	private String animalAgeMonths;
	private String ageLabel;
	private String species;
	private String breeds;
	
	 PatientInfo() {
	}
	public PatientInfo(String animalName ,
			String animalSex ,
			String animalWeight ,
			String animalWeightUom ,
			String animalAgeYears ,
			String animalAgeMonths ,
			String ageLabel ,
			String species ,
			String breeds ) {
		
		this.animalName =  animalName;
		this.animalSex =  animalSex;
		this.animalWeight = animalWeight ;
		this.animalWeightUom = animalWeightUom ;
		this.animalAgeYears = animalAgeYears ;
		this.animalAgeMonths = animalAgeMonths ;
		this.ageLabel = ageLabel ;
		this.species =  species;
		this.breeds =  breeds;
		
	}

	public String getAnimalName() {
		return animalName;
	}

	public String getAnimalSex() {
		return animalSex;
	}

	public String getAnimalWeight() {
		return animalWeight;
	}

	public String getAnimalWeightUom() {
		return animalWeightUom;
	}

	public String getAnimalAgeYears() {
		return animalAgeYears;
	}

	public String getAnimalAgeMonths() {
		return animalAgeMonths;
	}

	public String getAgeLabel() {
		return ageLabel;
	}

	public String getSpecies() {
		return species;
	}

	public String getBreeds() {
		return breeds;
	}


}

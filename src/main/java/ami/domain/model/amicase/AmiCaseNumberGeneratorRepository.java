package ami.domain.model.amicase;

public interface AmiCaseNumberGeneratorRepository {

	int getNextAmiCase();

	int getNextHospitalId();

}

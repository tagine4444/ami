package ami.domain.model.amicase.amiservices;

import java.util.List;

public interface AmiServicesRepository {


	AmiServiceMap getAmiServiceMap() ;
	List<Services> getAmiServices(AmiServiceCategory serviceCategory) ;
	void save(List<Services> amiServices);
	List<Services> getAmiServices();
}

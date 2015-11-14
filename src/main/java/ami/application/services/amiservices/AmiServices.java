package ami.application.services.amiservices;

import java.util.List;

import ami.domain.model.amicase.amiservices.AmiServiceCategory;
import ami.domain.model.amicase.amiservices.AmiServiceMap;
import ami.domain.model.amicase.amiservices.Services;

public interface AmiServices {


	AmiServiceMap getAmiServiceMap() ;
	List<Services> getAmiServices(AmiServiceCategory serviceCategory) ;
	void save(List<Services> amiServices);
	List<Services> getAmiServices();
}

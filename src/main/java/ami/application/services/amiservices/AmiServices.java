package ami.application.services.amiservices;

import java.util.List;

import ami.domain.referencedata.amiservices.Services;
import ami.domain.referencedata.amiservices.AmiServiceCategory;
import ami.domain.referencedata.amiservices.AmiServiceMap;

public interface AmiServices {


	AmiServiceMap getAmiServiceMap() ;
	List<Services> getAmiServices(AmiServiceCategory serviceCategory) ;
	void save(List<Services> amiServices);
	List<Services> getAmiServices();
}

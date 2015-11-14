package ami.application.services.amiservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ami.domain.model.amicase.amiservices.AmiServiceCategory;
import ami.domain.model.amicase.amiservices.AmiServiceMap;
import ami.domain.model.amicase.amiservices.Services;

@Service
public class AmiServicesImpl implements AmiServices {
	
	public final static String REFDATA_SERVICES = "refdataServices";
	
	@Autowired
	private MongoTemplate mongo;

	@Autowired
	private Environment env;
	
	
	@Override
	public List<Services> getAmiServices() {
		
		List<Services> amiSevices = mongo.findAll(Services.class,REFDATA_SERVICES);
		
		return amiSevices;
	}

	@Override
	public AmiServiceMap getAmiServiceMap() {
		
		List<Services> amiSevices = mongo.findAll(Services.class,REFDATA_SERVICES);
		
		if(amiSevices ==null || amiSevices.isEmpty() ){
			throw new RuntimeException("No Services Map found" );
		}
		
		return new AmiServiceMap(amiSevices);
	}

	@Override
	public List<Services> getAmiServices(AmiServiceCategory serviceCategory) {
		List<Services> amiSevices = mongo.findAll(Services.class,REFDATA_SERVICES);
		
		if(amiSevices ==null || amiSevices.isEmpty() ){
			throw new RuntimeException("No Services Map found" );
		}
		else if(amiSevices.size()>0 ){
			throw new RuntimeException("Only One Services Map should exist. Fond " + amiSevices.size());
		}
		return new AmiServiceMap(amiSevices).getAmiServices(serviceCategory);
		
	}

	@Override
	
	public  void save(List<Services>  amiServices) {
		
		for (Services amiService : amiServices) {
			mongo.save(amiService, REFDATA_SERVICES);
		}
	}

}

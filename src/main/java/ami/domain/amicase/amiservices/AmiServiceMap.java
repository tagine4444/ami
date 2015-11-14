package ami.domain.amicase.amiservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmiServiceMap {
	
	private Map<String, List<Services>> map;
	
	public AmiServiceMap(List<Services>amiServices) {
		
		this.map = new HashMap<String, List<Services>>();
		
		for (Services amiService : amiServices) {
			
			List<Services> amiServicesForCategory = this.map.get(amiService.getCategoryCode());
			if(amiServicesForCategory==null){
				amiServicesForCategory = new ArrayList<Services>();
			}
			amiServicesForCategory.add(amiService);
			
			this.map.put(amiService.getCategoryCode(), amiServicesForCategory);
		}
	}

	public List<Services> getAmiServices(AmiServiceCategory serviceCategory) {
		return this.map.get(serviceCategory);
	}
	
}

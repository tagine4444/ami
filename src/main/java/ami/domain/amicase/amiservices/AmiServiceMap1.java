package ami.domain.amicase.amiservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AmiServiceMap1 extends HashMap<String, List<Services>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8254508844641451953L;

	public AmiServiceMap1(List<Services>amiServices) {
		
		for (Services amiService : amiServices) {
			
			List<Services> amiServicesForCategory = this.get(amiService.getCategoryCode());
			if(amiServicesForCategory==null){
				amiServicesForCategory = new ArrayList<Services>();
			}
			amiServicesForCategory.add(amiService);
			
			this.put(amiService.getCategoryCode(), amiServicesForCategory);
		}
		
	}
	
}

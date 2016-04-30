package ami.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.domain.model.amicase.amiservices.AmiServiceMap1;
import ami.domain.model.amicase.amiservices.AmiServicesRepository;
import ami.domain.model.amicase.amiservices.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AmiServiceController {
	
	@Autowired
	private AmiServicesRepository animalService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping(value = "/services", method = RequestMethod.GET)
	@ResponseBody
	public String getAnimals(Model model) throws JsonProcessingException {
		
//		AmiServiceMap amiServiceMap = animalService.getAmiServiceMap();
		List<Services> services = animalService.getAmiServices();
		
		AmiServiceMap1 map1 = new AmiServiceMap1(services);
		String servicesString = objectMapper.writeValueAsString(map1);
		System.out.println(servicesString);
		
		return servicesString;
		
	}
}

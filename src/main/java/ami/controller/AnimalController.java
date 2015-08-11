package ami.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ami.application.services.animals.AnimalService;
import ami.domain.referencedata.Animals;

@Controller
public class AnimalController {

	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@RequestMapping(value = "/ami/animals", method = RequestMethod.GET)
	@ResponseBody
	public String addAnimals(Model model) throws JsonProcessingException {
		
		List<Animals> animals = animalService.getAnimals();
		String animalsString = objectMapper.writeValueAsString(animals);
		System.out.println(animalsString);
		
		return animalsString;
		
	}
}

package ami.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ami.application.services.animals.AnimalService;
import ami.domain.referencedata.Animals;
import ami.model.users.AmiAdminAuthority;
import ami.model.users.AmiUser;
import ami.model.users.AmiUserAuthority;

@Controller
public class InitController {
	
	
private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	
	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private Environment env;
	
	
	@RequestMapping(value = "/ami/init/user", method = RequestMethod.GET)
	public String addUsers(Model model) {
		
		List<AmiAdminAuthority> adminList =  new ArrayList<AmiAdminAuthority>();
		AmiAdminAuthority admin = new AmiAdminAuthority();
		adminList.add(admin);
		
		List<AmiUserAuthority> userList =  new ArrayList<AmiUserAuthority>();
		AmiUserAuthority user = new AmiUserAuthority();
		userList.add(user);
		
		AmiUser vet = new AmiUser("vet", "vet","Pet Hospital",userList);
		mongo.insert(vet);
		
		AmiUser chuck = new AmiUser("chuck", "chuck","Animal Medical Imaging", adminList);
		mongo.insert(chuck);
		
		vet = mongo.findById(vet.getId(), AmiUser.class);
		chuck = mongo.findById(chuck.getId(), AmiUser.class);
		log.debug("Found "+vet);
		log.debug("Found "+chuck);
		
		return "redirect:amicusthome";
	}
	
	@RequestMapping(value = "/ami/init/animals", method = RequestMethod.GET)
	public String addAnimals(Model model) {
		
		
		
		String canines       = (String) env.getProperty("Canine");
		String[] canineArray = canines.split(",");
		List<String> canineBreedList  =  Arrays.asList(canineArray);
		Animals canine = new Animals("Canine", canineBreedList);
		animalService.addAnimals(canine);
		
		
		
		String felines       = (String)env.getProperty("Feline");
		String[] felineArray = felines.split(",");
		List<String> felineBreedList  =  Arrays.asList(felineArray);
		Animals feline = new Animals("Feline", felineBreedList);
		animalService.addAnimals(feline);
		
		String bovines       = (String)env.getProperty("Bovine");
		String[] bovineArray = bovines.split(",");
		List<String> bovineBreedList  =  Arrays.asList(bovineArray);
		Animals bovine = new Animals("Bovine", bovineBreedList);
		animalService.addAnimals(bovine);
		
		String birds       = (String)env.getProperty("Birds");
		String[] birdArray = birds.split(",");
		List<String> birdBreedList  =  Arrays.asList(birdArray);
		Animals bird = new Animals("Birds", birdBreedList);
		animalService.addAnimals(bird);
		
		String others       = (String)env.getProperty("Others");
		String[] othersArray = others.split(",");
		List<String> othersArrayBreedList  =  Arrays.asList(othersArray);
		Animals other = new Animals("Others", othersArrayBreedList);
		animalService.addAnimals(other);
		
		
		return "redirect:amicusthome";
	}

}

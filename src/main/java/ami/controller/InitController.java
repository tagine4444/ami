package ami.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.application.services.amiservices.AmiServices;
import ami.application.services.animals.AnimalService;
import ami.application.services.security.AmiUserService;
import ami.application.services.security.HospitalService;
import ami.domain.amiusers.AmiUser;
import ami.domain.amiusers.AmiUserAuthority;
import ami.domain.hospitals.Hospital;
import ami.domain.hospitals.Phone;
import ami.domain.referencedata.Animals;
import ami.domain.referencedata.amiservices.AmiServiceCategory;
import ami.domain.referencedata.amiservices.Services;
import ami.domain.security.AmiAdminAuthority;
import ami.domain.security.AmiMasterAuthority;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class InitController {
	
	
private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	
	@Autowired
	private AnimalService animalService;
	
	@Autowired
	private AmiServices amiServices;
	
	@Autowired
	private Environment env;
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private AmiUserService amiUserService;
	
	
	
	@RequestMapping(value = "/ami/init/user", method = RequestMethod.GET)
	public String addUsers(Model model) {
/*	
		List<AmiAdminAuthority> adminList =  new ArrayList<AmiAdminAuthority>();
		AmiAdminAuthority admin = new AmiAdminAuthority();
		adminList.add(admin);
		
		List<AmiUserAuthority> userList =  new ArrayList<AmiUserAuthority>();
		AmiUserAuthority user = new AmiUserAuthority();
		userList.add(user);
		
		DateTime now = new DateTime();
		
		AmiUser vet = new AmiUser("vet", "vet","Pet Hospital", 11, now, userList);
		mongo.insert(vet,"amiuser1");
		
		AmiUser chuck = new AmiUser("chuck", "chuck","Animal Medical Imaging",12, now,  adminList);
		mongo.insert(chuck,"amiuser1");
		
		vet = mongo.findById(vet.getId(), AmiUser.class,"amiuser1");
		chuck = mongo.findById(chuck.getId(), AmiUser.class,"amiuser1");
		log.debug("Found "+vet);
		log.debug("Found "+chuck);
		
*/	
		
		
		
		
		try {
			
			DateTime now = new DateTime();
			
			
			// create hospital
			final String hospitalName = "Animal Medical Imaging";
			Hospital amiHospital = getHospital(hospitalName);
			hospitalService.createHospital(amiHospital, now);
			Hospital savedHospital = hospitalService.findHospitalbyName(hospitalName);
			
			// create user
			List<GrantedAuthority> adminList =  new ArrayList<GrantedAuthority>();
			GrantedAuthority master = new AmiMasterAuthority();
			GrantedAuthority admin  = new AmiAdminAuthority();
			adminList.add(master);
			adminList.add(admin);
			
			AmiUser chuck = new AmiUser("chuck", "chuck",hospitalName,savedHospital.getId(), now,  adminList);
			amiUserService.createAmiUser(savedHospital.getId(), savedHospital.getName(), chuck);
			
			
			
			// create hospital
			final String hospitalName2 = "Pet Clinic";
			Hospital petClinicHospital = getHospital(hospitalName2);
			hospitalService.createHospital(petClinicHospital, now);
			Hospital savedHospital2 = hospitalService.findHospitalbyName(hospitalName2);
			
			
			// create user
			List<GrantedAuthority> authoritiesWithMaster =  new ArrayList<GrantedAuthority>();
			GrantedAuthority amiUserAuth = new AmiUserAuthority();
			GrantedAuthority masterAuth  = new AmiMasterAuthority();
			authoritiesWithMaster.add(masterAuth);
			authoritiesWithMaster.add(amiUserAuth);
			AmiUser vetMaster = new AmiUser("vetmaster", "vetmaster",hospitalName2, savedHospital2.getId(), now, authoritiesWithMaster);
			amiUserService.createAmiUser(savedHospital2.getId(), savedHospital2.getName(), vetMaster);
						
			// create user
			List<AmiUserAuthority> userList =  new ArrayList<AmiUserAuthority>();
			AmiUserAuthority user = new AmiUserAuthority();
			userList.add(user);
			AmiUser vet = new AmiUser("vet", "vet",hospitalName2, savedHospital2.getId(), now, userList);
			amiUserService.createAmiUser(savedHospital2.getId(), savedHospital2.getName(), vet);
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "redirect:amicusthome";
	}
	
	

	@RequestMapping(value = "/ami/init/animals", method = RequestMethod.GET)
	@ResponseBody
	public String addAnimals(Model model) {
		
		int index = 0;
		List<Animals> animalList = new ArrayList<Animals>();
		
		String canines       = (String) env.getProperty("Canine");
		String[] canineArray = canines.split(",");
		List<String> canineBreedList  =  Arrays.asList(canineArray);
		Animals canine = new Animals(String.valueOf(index++), "Canine", canineBreedList);
		animalService.addAnimals(canine);
		animalList.add(canine);
		
		String felines       = (String)env.getProperty("Feline");
		String[] felineArray = felines.split(",");
		List<String> felineBreedList  =  Arrays.asList(felineArray);
		Animals feline = new Animals(String.valueOf(index++), "Feline", felineBreedList);
		animalService.addAnimals(feline);
		animalList.add(feline);
		
		String bovines       = (String)env.getProperty("Bovine");
		String[] bovineArray = bovines.split(",");
		List<String> bovineBreedList  =  Arrays.asList(bovineArray);
		Animals bovine = new Animals(String.valueOf(index++), "Bovine", bovineBreedList);
		animalService.addAnimals(bovine);
		animalList.add(bovine);
		
		String birds       = (String)env.getProperty("Birds");
		String[] birdArray = birds.split(",");
		List<String> birdBreedList  =  Arrays.asList(birdArray);
		Animals bird = new Animals(String.valueOf(index++), "Birds", birdBreedList);
		animalService.addAnimals(bird);
		animalList.add(bird);
		
		String others       = (String)env.getProperty("Others");
		String[] othersArray = others.split(",");
		List<String> othersArrayBreedList  =  Arrays.asList(othersArray);
		Animals other = new Animals(String.valueOf(index++), "Others", othersArrayBreedList);
		animalService.addAnimals(other);
		animalList.add(other);
		
		String result ="{}";
		try {
			result = mapper.writeValueAsString(animalList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@RequestMapping(value = "/ami/init/services", method = RequestMethod.GET)
	@ResponseBody
	public String addServices(Model model) {
		
//		contrastRadiography
//		computedTomography
//		radiographyFluoroscopy
//		ultrasound
		
		
		
		List<Services> amiServiceList = new ArrayList<Services>();
		
		
		AmiServiceCategory category = AmiServiceCategory.CONTRAST_RADIOGRAPHY;
		String contrastRadiography       = (String) env.getProperty(category.getCode());
		String[] contrastRadiographyArray = contrastRadiography.split(",");
		List<String> contrastRadiographyList  =  Arrays.asList(contrastRadiographyArray);
		for (String aServiceName : contrastRadiographyList) {
			
			Services contrastRadiographies = new Services(category.getCode(), category.getName() , aServiceName);
			amiServiceList.add(contrastRadiographies);
		}
		
		 
		 category = AmiServiceCategory.COMPUTED_TOMOGRAPHY;
		 contrastRadiography       = (String) env.getProperty(category.getCode());
		 contrastRadiographyArray = contrastRadiography.split(",");
		 contrastRadiographyList  =  Arrays.asList(contrastRadiographyArray);
		 for (String aServiceName : contrastRadiographyList) {
			
			Services contrastRadiographies = new Services(category.getCode(), category.getName() , aServiceName);
			amiServiceList.add(contrastRadiographies);
		 }
		 
		 category = AmiServiceCategory.RADIOGRAPHY_FLUOROSCOPY;
		 contrastRadiography       = (String) env.getProperty(category.getCode());
		 contrastRadiographyArray = contrastRadiography.split(",");
		 contrastRadiographyList  =  Arrays.asList(contrastRadiographyArray);
		 for (String aServiceName : contrastRadiographyList) {
			 
			 Services contrastRadiographies = new Services(category.getCode(), category.getName() , aServiceName);
			 amiServiceList.add(contrastRadiographies);
		 }
		 
		 category = AmiServiceCategory.ULTRASOUND;
		 contrastRadiography       = (String) env.getProperty(category.getCode());
		 contrastRadiographyArray = contrastRadiography.split(",");
		 contrastRadiographyList  =  Arrays.asList(contrastRadiographyArray);
		 for (String aServiceName : contrastRadiographyList) {
			
			Services contrastRadiographies = new Services(category.getCode(), category.getName() , aServiceName);
			amiServiceList.add(contrastRadiographies);
		 }
		 
		 amiServices.save(amiServiceList);
		
		String result ="{}";
		try {
			result = mapper.writeValueAsString(amiServiceList);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return result;
	
	}
	
	
	private Hospital getHospital(String hospitalName) {
		
		String id = String.valueOf(System.currentTimeMillis());
		String address = "1100 Main Street, WA 98221 USA";
		String mainPhone = "321 213 6732";
		String mainFax = "212 221 9876";
		String fax2 =  "212 221 9876";
		
		Phone phone = new Phone("Cell Phone", "298 334 9832");
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		phones.add(phone);
		phones.add(phone);
		


		Hospital hospital = new Hospital( id,
				hospitalName,
				 address,
				 mainPhone,
				 mainFax,
				 fax2,
				 phones);
		return hospital;
	}
	
	

}

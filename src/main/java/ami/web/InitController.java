package ami.web;

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

import ami.domain.model.amicase.AnimalRepository;
import ami.domain.model.amicase.Animals;
import ami.domain.model.amicase.amiservices.AmiServiceCategory;
import ami.domain.model.amicase.amiservices.AmiServicesRepository;
import ami.domain.model.amicase.amiservices.Services;
import ami.domain.model.security.AmiAdminAuthority;
import ami.domain.model.security.AmiMasterAuthority;
import ami.domain.model.security.amiusers.AmiUser;
import ami.domain.model.security.amiusers.AmiUserAuthority;
import ami.domain.model.security.amiusers.AmiUserRepository;
import ami.domain.model.security.hospitals.Address;
import ami.domain.model.security.hospitals.ContractEnum;
import ami.domain.model.security.hospitals.Email;
import ami.domain.model.security.hospitals.Hospital;
import ami.domain.model.security.hospitals.HospitalAccountSize;
import ami.domain.model.security.hospitals.HospitalRepository;
import ami.domain.model.security.hospitals.Phone;
import ami.infrastructure.services.AmiServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class InitController {
	
	
private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	
	@Autowired
	private AnimalRepository animalService;
	
	@Autowired
	private AmiServicesRepository amiServices;
	
	@Autowired
	private Environment env;
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private HospitalRepository hospitalService;
	
	@Autowired
	private AmiServices amiInfrastructureService;
	
	@Autowired
	private AmiUserRepository amiUserService;
	
	
	
	@RequestMapping(value = "/ami/init/users", method = RequestMethod.GET)
	public String addUsers(Model model) {
		
		try {
			
			DateTime now = new DateTime();
			
			
			// create hospital
			final String hospitalName = "Animal Medical Imaging";
			final String acronym = "";
			String contract = ContractEnum.NOT_CONTRACT.getName();
			String accountSize = HospitalAccountSize.SMALL.getName();
			Hospital amiHospital = getHospital(hospitalName,acronym,contract,accountSize);
			amiInfrastructureService.createHospital(amiHospital, now);
			Hospital savedHospital = hospitalService.findHospitalbyName(hospitalName);
			
			// create user
			List<GrantedAuthority> adminList =  new ArrayList<GrantedAuthority>();
			GrantedAuthority master = new AmiMasterAuthority();
			GrantedAuthority admin  = new AmiAdminAuthority();
			adminList.add(master);
			adminList.add(admin);
			
			AmiUser chuck = new AmiUser("chuck", "chuck","Charles", "Root","tagine4444@yahoo.com","Radiologist",hospitalName,savedHospital.getId(), now,  adminList);
			amiUserService.createAmiUser(savedHospital.getId(), savedHospital.getName(), chuck);
			
			// create hospital
			final String hospitalName2 = "Pet Clinic";
			final String acronym1 = "PETCLI";
			contract = ContractEnum.CONTRACT.getName();
			accountSize = HospitalAccountSize.MEDIUM.getName();
			Hospital petClinicHospital = getHospital(hospitalName2,acronym1,contract,accountSize);
			amiInfrastructureService.createHospital(petClinicHospital, now);
			Hospital savedHospital2 = hospitalService.findHospitalbyName(hospitalName2);
			
			
			// create user
			List<GrantedAuthority> authoritiesWithMaster =  new ArrayList<GrantedAuthority>();
			GrantedAuthority amiUserAuth = new AmiUserAuthority();
			GrantedAuthority masterAuth  = new AmiMasterAuthority();
			authoritiesWithMaster.add(masterAuth);
			authoritiesWithMaster.add(amiUserAuth);
			AmiUser vetMaster = new AmiUser("vetmaster", "vetmaster","Peter", "Gabriel","tagine4444@yahoo.com","Veterinarian", hospitalName2, savedHospital2.getId(), now, authoritiesWithMaster);
			amiUserService.createAmiUser(savedHospital2.getId(), savedHospital2.getName(), vetMaster);
						
			// create user
			List<GrantedAuthority> userList =  new ArrayList<GrantedAuthority>();
			AmiUserAuthority user = new AmiUserAuthority();
			userList.add(user);
			AmiUser vet = new AmiUser("vet", "vet", "Johny", "Cash","tagine4444@yahoo.com", "Technician", hospitalName2, savedHospital2.getId(), now, userList);
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
	
	
	private Hospital getHospital(String hospitalName, String acronym, String contract, String accountSize) {
		
		String id = String.valueOf(System.currentTimeMillis());
		
		List<Address> addresses = new ArrayList<Address>();
		Address address = new Address( "Main", "1100 Main Street, WA 98221 USA");
		Address billable = new Address( "Billable", "PO Box 3993 New York");
		addresses.add(address);
		addresses.add(billable);
		
		Phone phone = new Phone("Cell Phone", "298 334 9832");
		Phone fax = new Phone("Fax", "298 334 9832");
		Phone mainPhone = new Phone("Main Phone", "321 213 6732");
		List<Phone> phones = new ArrayList<Phone>();
		phones.add(phone);
		phones.add(fax);
		phones.add(mainPhone);

		
		Email email1 = new Email("Main", "company.email@yahoo.com");
		Email email2 = new Email("Personal", "company.email@yahoo.com");
		List<Email> emails = new ArrayList<Email>();
		emails.add(email1);
		emails.add(email2);
		
		final String notes ="This hospital is very nice so treat it well";

		Hospital hospital = new Hospital( id,
				hospitalName,
				acronym,
				addresses,
				 phones,
				emails,
				notes,
				contract, 
				accountSize
				);
		
		return hospital;
	}
	
	

}

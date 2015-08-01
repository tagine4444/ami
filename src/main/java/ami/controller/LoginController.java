package ami.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ami.model.users.AmiAuthtorities;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	public static final String UPLOAD_DIR = "uploads";
	
	
	
	
	
	
	@RequestMapping(value = "/ami/getuserid", method = RequestMethod.GET,  produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getUserId() {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		final String result = "{\"userName\": \""+ userName +"\"}";
		return result;
			
	}
	
	
//	@RequestMapping("/greeting")
//    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "greeting";
//    }
//	
//	
//	@RequestMapping(value = "/ami", method = RequestMethod.GET)
//	public String home(Model model) {
//		
//		System.out.println(">>>>>> going home");
//		
//		return "index";
//	}
	
//	@RequestMapping(value = "/ami/login", method = RequestMethod.GET)
//	public String login(Model model) {
//		
//		System.out.println(">>>>>> going to amicust!!!!");
//		return "login";
//	}
	
	
//	@RequestMapping(value = "/ami/amicusthome1", method = RequestMethod.GET)
//	public String amiLogin(Model model) {
//		
//		
//		Set<String> permissions = new HashSet();
//		for (GrantedAuthority auth : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
//		    permissions.add(auth.getAuthority());
//		}
//
//		if (permissions.contains("USER_ROLE") ) {
//		    //do stuff
//		}
//		
//		System.out.println("in amicusthome1 >>>>>> redirect:amicusthome!!!");
//		return "redirect:amicusthome";
//	}
	
	
	@RequestMapping(value = "/ami/logincheck", method = RequestMethod.GET)
	public String amiLoginGET(Model model) {
		
		System.out.println("GET CHECK >>>>>> going to amicust!!!!");
		return "redirect:amicusthome1";
	}
	
	
	
	
	@RequestMapping(value = "/ami/save", method = RequestMethod.GET)
	public String amiSave(Model model) {
		
		Person p = new Person("Joe", 34);
		mongo.insert(p);
		
		p = mongo.findById(p.getId(), Person.class);
		log.debug("Found "+p);
		System.out.println("found "+p);
		
		return "redirect:amicusthome";
	}

	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"') or hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSave(@RequestBody String newAmiRequest ) {
		
		System.out.println(newAmiRequest);
		
		//Person p = new Person("Joe", 34);
		
		
		DBObject dbObject = (DBObject) JSON.parse(newAmiRequest);
		
		//mongo.save(newAmiRequest, "request");
		mongo.save(dbObject, "request");
		System.out.println(dbObject.get("_id"));
		
		Object object = dbObject.get("_id");
		
		
		
		
		//p = mongo.findById(p.getId(), Person.class);
		//log.debug("Found "+p);
		//System.out.println("found "+p);
		
		
		
		return "{\"id\": \" "+object.toString()+" \"}";
	}
	
	
		
	
}

package ami.web;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ami.domain.model.security.AmiAdminAuthority;
import ami.domain.model.security.AmiAuthtorities;

@Controller
public class LoginController {
	
	
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
//	@Autowired
//	private MongoTemplate mongo;
	
//	public static final String UPLOAD_DIR = "uploads";
	
//	@Autowired
//	private CommandGateway commandGateway;
//	@Autowired
//	private AmiRequestService amiRequestService;
	
	
	@RequestMapping(value = "/ami/amilogin", method = RequestMethod.GET)
	public String amiLoginGET(Model model) {
		
		boolean isAdmiUser = false;
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			
			String authority = grantedAuthority.getAuthority();
			if( AmiAdminAuthority.isAdmin(authority)){
				isAdmiUser = true;
				break;
			}
		}
		
		if( isAdmiUser){
			return "redirect:amiadmin";
		}
		
		return "redirect:amicust";
		
	}
	
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_ADMIN+"')")
	@RequestMapping(value = "/ami/amiadmin", method = RequestMethod.GET)
	public String adminLogin(Model model) {
		
		return "amiadminhome";
		
	}
	
	@PreAuthorize("hasAuthority('"+AmiAuthtorities.AMI_USER+"')")
	@RequestMapping(value = "/ami/amicust", method = RequestMethod.GET)
	public String customerLogin(Model model) {
		
		return "amicusthome";
		
	}
	
	
//	@RequestMapping(value = "/ami/save", method = RequestMethod.GET)
//	public String amiSave(Model model) {
//		
//		Person p = new Person("Joe", 34);
//		mongo.insert(p);
//		
//		p = mongo.findById(p.getId(), Person.class);
//		log.debug("Found "+p);
//		System.out.println("found "+p);
//		
//		return "redirect:amicusthome";
//	}


	
}

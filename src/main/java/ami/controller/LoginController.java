package ami.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
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
	
//	@RequestMapping(value = "/login", method = RequestMethod.GET)
//	public String login(Model model) {
//		
//		
//		return "login";
//	}
	@RequestMapping(value = "/ami/logincheck", method = RequestMethod.POST)
	public String amiLogin(Model model) {
		
		System.out.println(">>>>>> going to amicust!!!!");
		return "redirect:amicusthome";
	}

}

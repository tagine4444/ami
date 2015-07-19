package ami.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private MongoTemplate mongo;
	
	public static final String UPLOAD_DIR = "uploads";
	
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
	
	
	
	
	@RequestMapping(value = "/ami/save", method = RequestMethod.GET)
	public String amiSave(Model model) {
		
		Person p = new Person("Joe", 34);
		mongo.insert(p);
		
		p = mongo.findById(p.getId(), Person.class);
		log.debug("Found "+p);
		System.out.println("found "+p);
		
		return "redirect:amicusthome";
	}

	@RequestMapping(value = "/ami/amicusthome/amirequest", method = RequestMethod.POST)
	@ResponseBody
	public String amiRequestSave(@RequestBody String newAmiRequest ) {
		
		System.out.println(newAmiRequest);
		
		//Person p = new Person("Joe", 34);
		mongo.insert(newAmiRequest, "request");
		
		//p = mongo.findById(p.getId(), Person.class);
		//log.debug("Found "+p);
		//System.out.println("found "+p);
		
		return "{}";
	}
	
	
		
	
}

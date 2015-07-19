package ami;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@EnableWebMvc
@ComponentScan
@EnableAutoConfiguration
//@SpringBootApplication
public class Application {
	
	 public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	    }

}

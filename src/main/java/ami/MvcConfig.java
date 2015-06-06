package ami;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/ami").setViewName("index");
    	
    	registry.addViewController("/ami/index").setViewName("index");
    	registry.addViewController("/ami/login").setViewName("login");
       registry.addViewController("/ami/amicusthome").setViewName("amicusthome");
    }
}

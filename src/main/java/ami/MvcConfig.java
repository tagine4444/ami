package ami;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mongodb.MongoClient;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/ami").setViewName("index");
    	
    	registry.addViewController("/ami/index").setViewName("index");
    	registry.addViewController("/ami/login").setViewName("login");
       registry.addViewController("/ami/amicusthome").setViewName("amicusthome");
    }
    
    
    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
       // UserCredentials userCredentials = new UserCredentials("joe", "secret");
        return new SimpleMongoDbFactory(new MongoClient(), "ami");
      }

      public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
      }
     
}

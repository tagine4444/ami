package ami;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import ami.web.converters.DateTimeToStringConverter;
import ami.web.converters.StringToDateTimeConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter { 

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	
       registry.addViewController("/ami/index").setViewName("index");
       
       
       registry.addViewController("/ami/home").setViewName("home");
       registry.addViewController("/ami").setViewName("index");
       registry.addViewController("/ami/login").setViewName("login");
       registry.addViewController("/ami/adminlogin").setViewName("adminlogin");
    }
    
    
    public @Bean MongoDbFactory mongoDbFactory() throws Exception {
       // UserCredentials userCredentials = new UserCredentials("joe", "secret");
        return new SimpleMongoDbFactory(new MongoClient(), "ami");
      }

      public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory(),mongoConverter());
      }
      
      @Bean
      public CustomConversions customConversions() {
          List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
          converters.add(new StringToDateTimeConverter());
          converters.add(new DateTimeToStringConverter());
          
//          converters.add(new DateToStringConverter());
//          converters.add(new StringToDateConverter());
          
          return new CustomConversions(converters);
      }

      @Bean
      public MappingMongoConverter mongoConverter() throws Exception {
          MongoMappingContext mappingContext = new MongoMappingContext();
          DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
          MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mappingContext);
          mongoConverter.setCustomConversions(customConversions());
          mongoConverter.afterPropertiesSet();
          return mongoConverter;
      }
      
//      @Primary
      public @Bean ObjectMapper objectMapper()  {
    	  ObjectMapper om = new ObjectMapper();
    	  om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
                  false);
    	  return om;
      }
      
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
           registry.addResourceHandler("/uploads/**").addResourceLocations("file:///Users/chidra/dev/sts/sts-bundle/sts-3.6.3.RELEASE/workspace/ami/uploads");
      }
      
     
}

package ami;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@EnableCaching
public class Application {
	
	@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("animals");
    }
	
	 public static void main(String[] args) {
	        SpringApplication.run(Application.class, args);
	 }

}

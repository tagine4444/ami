package ami;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@EnableAutoConfiguration
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

//	@Bean
//	public CacheManager cacheManager() {
//		return new ConcurrentMapCacheManager("amicache");
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}

package ami;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
//@ConditionalOnClass({ Servlet.class, StandardServletMultipartResolver.class, MultipartConfigElement.class })
//@ConditionalOnProperty(prefix = "multipart", name = "enabled", matchIfMissing = true)
//@ConditionalOnProperty(prefix="multipart",relaxedNames=true, value="enabled")
//@EnableConfigurationProperties(MultipartProperties.class)
public class MultipartAutoConfiguration {

	
	@Autowired
	private MultipartProperties multipartProperties = new MultipartProperties();

	@Bean
	//@ConditionalOnMissingBean
	public MultipartConfigElement multipartConfigElement() {
		
		this.multipartProperties.setMaxFileSize("5120MB");
		this.multipartProperties.setMaxRequestSize("5120MB");
		return this.multipartProperties.createMultipartConfig();
	}

	@Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
	//@ConditionalOnMissingBean(value = MultipartResolver.class)
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}

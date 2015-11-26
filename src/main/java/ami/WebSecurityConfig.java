package ami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import ami.domain.model.security.AmiAuthenticationProvider;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private AmiAuthenticationProvider amiAuthenticationProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/","/ami/index",  "/ami/login","/ami/getuserid", "/ami/newuser", "/ami/init/user").permitAll()
        .anyRequest().authenticated()
        .and()
    .formLogin()
        .loginPage("/ami/login")
        .permitAll()
        .defaultSuccessUrl("/ami/amilogin")
        .and()
    .logout()
        .logoutUrl("/j_spring_security_logout")
        .logoutSuccessUrl("/ami/login")
        .invalidateHttpSession( true )
        .deleteCookies("JSESSIONID");   
//        .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//        auth
//        .inMemoryAuthentication()
//            .withUser("vet").password("vet").roles("USER");
//        auth
//        .inMemoryAuthentication()
//        .withUser("chuck").password("chuck").roles("USER");
    	
    	
    	auth.authenticationProvider(amiAuthenticationProvider);
                
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/ami/resources/**"); // #3
    }

}

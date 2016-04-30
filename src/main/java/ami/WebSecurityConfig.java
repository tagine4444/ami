package ami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import ami.domain.model.security.AmiAuthenticationProvider;

@Configuration
//@EnableWebSecurity
@AutoConfigureOrder(SecurityProperties.ACCESS_OVERRIDE_ORDER)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private AmiAuthenticationProvider amiAuthenticationProvider;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/","/index", "/login","/getuserid", "/newuser", "/init/users").permitAll()
        .anyRequest().authenticated()
        .and()
    .formLogin()
        .loginPage("/login")
        .permitAll()
//        .usernameParameter("username")
//        .passwordParameter("password")
//        .loginProcessingUrl(loginProcessingUrl)
        .defaultSuccessUrl("/amilogin")
        .and()
    .logout()
        .logoutUrl("/j_spring_security_logout")
        .logoutSuccessUrl("/login")
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
           .antMatchers("/resources/**","/static/**"); // #3
    }

}

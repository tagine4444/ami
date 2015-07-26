package ami;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/", "/ami/home", "/ami/getuserid").permitAll()
        .antMatchers("/ami/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
    .formLogin()
        .loginPage("/ami/login")
        .permitAll()
        .defaultSuccessUrl("/ami/amicusthome")
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
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
        

        auth
        .inMemoryAuthentication()
            .withUser("vet").password("vet").roles("USER");
        auth
        .inMemoryAuthentication()
        .withUser("chuck").password("chuck").roles("USER");
                
                
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
      web
        .ignoring()
           .antMatchers("/ami/resources/**"); // #3
    }

}

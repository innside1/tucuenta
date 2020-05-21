package co.adwork.stive.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Clase de configuracion de seguridad
 * @author juan.hoyos
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/actuator/**", "/v*/*/**").permitAll().anyRequest()
                .authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().contentTypeOptions().and().httpStrictTransportSecurity().includeSubDomains(false).and()
                .xssProtection().block(true).and().frameOptions().deny().and().headers().referrerPolicy();
//    	http.authorizeRequests().anyRequest().permitAll()  
//        .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers(
    			"/v2/api-docs", 
    			"/configuration/ui", 
    			"/swagger-resources/**", 
    			"/configuration/**", 
    			"/swagger-ui.html", 
    			"/webjars/**");
    }
}

package Engine.Application.Form.cv.engine.config;


import Engine.Application.Form.cv.engine.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.data.elasticsearch.core.query.Criteria.and;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringConfig extends WebSecurityConfiguration{

   @Autowired
    private JwtFilter jwtFilter;
   
  protected void configure(HttpSecurity http) throws Exception {
  
      http
              .httpBasic().disable()
              .csrf().disable()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeRequests()
              .antMatchers("/admin/*").hasRole("ADMIN")
              .antMatchers("/user/*").hasRole("USER")
              .antMatchers("/register", "/auth").permitAll()
              .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);    
  
  
  }
  
    
    @Bean
    public PasswordEncoder  passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
}

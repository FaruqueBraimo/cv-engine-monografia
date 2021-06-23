/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author faruq
 */
@Configuration
public class PassowordConfig {
    
    @Bean
    public PasswordEncoder  passwordEncoder () {
        return new BCryptPasswordEncoder(8);
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Application.Form.cv.engine.config.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 *
 * @author faruq
 */
@Component
@Log
public class JwtFilter  extends GenericFilterBean{

    @Override
    public void doFilter(ServletRequest sr, ServletResponse sr1, FilterChain fc) throws IOException, ServletException {



    }
    
}

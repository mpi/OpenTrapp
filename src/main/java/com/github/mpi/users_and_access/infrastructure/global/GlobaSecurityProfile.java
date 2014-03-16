package com.github.mpi.users_and_access.infrastructure.global;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.OncePerRequestFilter;

@Profile("no-security")
@Configuration
public class GlobaSecurityProfile {

    @Bean
    public GlobalSecurityContext securityContext(){
        return new GlobalSecurityContext();
    }
 
    @Bean(name="springSecurityFilterChain")
    public Filter securityFilter(){
        return new OncePerRequestFilter() {
            
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                
                filterChain.doFilter(request, response);
            }
        };
    }
}

package com.github.mpi.users_and_access.infrastructure.mock;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Profile("mock-security")
public class MockSecurityContext {

    @Autowired
    private Filter springSecurityFilterChain;
    
    private boolean mockModeEnabled = false;
    
    public void disableAtAll(){
        mockModeEnabled = false;
    }
    
    public void enableMockMode(){
        mockModeEnabled = true;
    }
    
    @Bean(name="securityFilterChain")
    public Filter securityFilterChain(){
        
        return new OncePerRequestFilter() {
            
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                
                if(mockModeEnabled){
                    springSecurityFilterChain.doFilter(request, response, filterChain);
                } else{
                    filterChain.doFilter(request, response);
                }
            }
        };
    }
}

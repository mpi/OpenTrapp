package com.github.mpi.users_and_access.application;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mpi.users_and_access.infrastructure.spring.OpenIDUserService;
import com.github.mpi.users_and_access.infrastructure.spring.OpenIDUserService.OpenIDUser;

@Controller
public class AuthenticationEndpoint {

    @RequestMapping(
            method   = GET,           
            value    = "/endpoints/v1/authentication/status")
    @ResponseStatus(OK)
    public @ResponseBody AuthenticationStatus status(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        boolean isAuthenticated = authentication.getPrincipal() instanceof OpenIDUserService.OpenIDUser;
        String fullName = "Anonymous";
        if(isAuthenticated){
            OpenIDUserService.OpenIDUser details = (OpenIDUser) authentication.getPrincipal();
            fullName = details.getFullName();
        }
        
        return new AuthenticationStatus(authentication.getName(), fullName, isAuthenticated);
    }
    
    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class AuthenticationStatus {

        String username, displayName;
        boolean authenticated;
        String loginUrl = "/endpoints/v1/authentication/login"; 
        String logoutUrl = "/endpoints/v1/authentication/logout"; 

        public AuthenticationStatus(String username, String displayName, boolean authenticated) {
            this.username = username;
            this.displayName = displayName;
            this.authenticated = authenticated;
        }
    }
}

package com.github.mpi.users_and_access.infrastructure.spring;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.github.mpi.users_and_access.domain.SecurityContext;
import com.github.mpi.users_and_access.domain.User;

@Profile("spring-security")
@Service
public class SpringSecurityContext implements SecurityContext{

    @Override
    public User authenticatedUser() {
        
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        
        if(details instanceof User){
            return (User) details;
        }
        
        return User.ANONYMOUS;
    }

}

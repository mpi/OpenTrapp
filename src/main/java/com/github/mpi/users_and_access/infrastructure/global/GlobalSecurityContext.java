package com.github.mpi.users_and_access.infrastructure.global;

import com.github.mpi.users_and_access.domain.SecurityContext;
import com.github.mpi.users_and_access.domain.User;

public class GlobalSecurityContext implements SecurityContext{

    private User authenticatedUser = User.ANONYMOUS;
    
    @Override
    public User authenticatedUser() {
        return authenticatedUser;
    }

    public void set(User user){
        authenticatedUser = user;
    }
    
    public void clear(){
        authenticatedUser = User.ANONYMOUS;
    }
}

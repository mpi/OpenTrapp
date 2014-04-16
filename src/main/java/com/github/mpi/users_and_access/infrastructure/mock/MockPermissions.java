package com.github.mpi.users_and_access.infrastructure.mock;

import com.github.mpi.users_and_access.application.Permissions;

public class MockPermissions implements Permissions {

    @Override
    public boolean canDelete(String id){
        return true;
    }
    
    @Override
    public boolean canModify(String id){
        return true;
    }
    
    @Override
    public boolean canCreate(String user){
        return true;
    }
    
}

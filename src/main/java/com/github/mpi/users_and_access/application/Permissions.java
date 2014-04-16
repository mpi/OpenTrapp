package com.github.mpi.users_and_access.application;

public interface Permissions {

    public abstract boolean canDelete(String id);

    public abstract boolean canModify(String id);

    public abstract boolean canCreate(String user);

}
package com.github.mpi.time_registration.domain;

public class ProjectName {

    private final String name;

    public ProjectName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object x) {
        
        if(!(x instanceof ProjectName)){
            return false;
        }
        
        ProjectName other = (ProjectName) x;
        return name.equals(other.name);
    }
    
    @Override
    public String toString() {
        return name;
    }
}

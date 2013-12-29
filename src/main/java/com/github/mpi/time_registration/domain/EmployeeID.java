package com.github.mpi.time_registration.domain;

public class EmployeeID {

    private final String id;

    protected EmployeeID() {
        // FIXME: cglib requires default constructor in order to proxy given class
        this("this is for cglib only");
    }
    
    public EmployeeID(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object x) {
        
        if(!(x instanceof EmployeeID)){
            return false;
        }
        
        EmployeeID other = (EmployeeID) x;
        
        return id.equals(other.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return id;
    }
}

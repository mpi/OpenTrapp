package com.github.mpi.time_registration.application;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.mpi.time_registration.domain.EmployeeContext;
import com.github.mpi.time_registration.domain.EmployeeID;

@Component
@Scope(value="request", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ManualEmployeeContext implements EmployeeContext {

    private EmployeeID employeeID = null;
    
    @Override
    public EmployeeID employeeID(){
        return employeeID;
    }
    
    public void enter(EmployeeID employeeID){
        this.employeeID = employeeID;
    }
    
    public void leave(){
        this.employeeID = null;
    }
}

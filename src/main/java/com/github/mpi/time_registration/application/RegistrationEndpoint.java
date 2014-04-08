package com.github.mpi.time_registration.application;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.RegistrationService;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
public class RegistrationEndpoint {

    @Autowired
    private RegistrationService service;

    @Autowired
    private ManualEmployeeContext context;
    
    @RequestMapping(
              method = POST, 
            consumes = "application/json",
               value = "/endpoints/v1/employee/{employeeID}/work-log/entries")
    @ResponseStatus(CREATED)
    @PreAuthorize("@registrationEndpoint.isCurrentEmployee(#employeeID)")
    public void submitEntry(@PathVariable String employeeID, @RequestBody Form form) {

        try{
            
            context.enter(new EmployeeID(employeeID));
            
            service.submit(form.workload, form.projectName, form.day);
                
        } catch (IllegalArgumentException e) {

            throw new InvalidExpressionException(e);
            
        } finally{
            
            context.leave();
        }
    }

    public boolean isCurrentEmployee(String employeeID) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth.getName().equals(employeeID);
    }

    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    public static class Form{

        String projectName, workload, day;
    }

    @ResponseStatus(BAD_REQUEST)
    public class InvalidExpressionException extends IllegalArgumentException {
        
        private static final long serialVersionUID = -981128848209981239L;
        
        public InvalidExpressionException(Throwable cause) {
            super(cause);
        }
    }
}
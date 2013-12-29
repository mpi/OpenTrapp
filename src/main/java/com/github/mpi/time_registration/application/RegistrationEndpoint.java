package com.github.mpi.time_registration.application;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.RegistrationService;

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
    public @ResponseBody ResponseJson submitEntry(HttpServletResponse response, @PathVariable String employeeID, @RequestBody Form form){

        String link = "/endpoints/v1/work-log/entries/WL.0001";

        try{
            
            context.enter(new EmployeeID(employeeID));
            
            service.submit(form.workLogExpression);
            response.setHeader("Location", link);
            
        } catch (IllegalArgumentException e) {

            throw new InvalidExpressionException(e);
            
        } finally{
            
            context.leave();
        }
        
        return new ResponseJson("SUCCESS", link);
    }
    

    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class ResponseJson{

        String status, link;

        ResponseJson(String status, String link) {
            this.status = status;
            this.link = link;
        }
        
    }
    
    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    static class Form{

        String workLogExpression;
    }

    @ResponseStatus(BAD_REQUEST)
    class InvalidExpressionException extends IllegalArgumentException {
        
        private static final long serialVersionUID = -981128848209981239L;
        
        public InvalidExpressionException(Throwable cause) {
            super(cause);
        }
    }
}


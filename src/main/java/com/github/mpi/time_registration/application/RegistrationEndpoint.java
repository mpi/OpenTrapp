package com.github.mpi.time_registration.application;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mpi.time_registration.domain.RegistrationService;

@Controller
public class RegistrationEndpoint {

    @Autowired
    private RegistrationService service;
    
    @RequestMapping(
            method   = POST, 
            consumes = "application/json",
            value    = "/endpoints/v1/work-log/entries")
    @ResponseStatus(CREATED)
    public @ResponseBody String submitEntry(HttpServletResponse response, @RequestBody Form form){

        try{
            
            service.submit(form.workLogExpression);
            response.setHeader("Location", "/endpoints/v1/work-log/entries/WL.0001");
            
        } catch (IllegalArgumentException e) {
            throw new InvalidExpressionException(e);
        }
        
        return "{\"status\": \"sucess\"}";
    }
    
    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    public static class Form{

        String workLogExpression;
        
    }

    @ResponseStatus(BAD_REQUEST)
    public class InvalidExpressionException extends IllegalArgumentException {
        
        private static final long serialVersionUID = -981128848209981239L;
        
        public InvalidExpressionException(Throwable cause) {
            super(cause);
        }
        
    }
}


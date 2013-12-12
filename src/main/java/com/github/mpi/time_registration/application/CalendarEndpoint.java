package com.github.mpi.time_registration.application;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class CalendarEndpoint {

    @RequestMapping(
            method   = GET, 
            value    = "/endpoints/v1/calendar/{id:.+}")
    @ResponseStatus(OK)
    public @ResponseBody MonthJson listMonth(@PathVariable String id){

        List<DayJson> days = new ArrayList<DayJson>();
        
        days.add(new DayJson("2014/01/01", true));
        days.add(new DayJson("2014/01/02", false));
        days.add(new DayJson("2014/01/03", false));
        
        return new MonthJson(days); 
    }

    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class MonthJson{
        
        String dupa = "dupa!!!";
        List<DayJson> days;

        public MonthJson(List<DayJson> days) {
            this.days = days;
        }
    }
    
    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class DayJson{
        
        String link, id;
        boolean holiday;
        
        DayJson(String id, boolean holiday){
            this.id = id;
            this.link = String.format("/endpoints/v1/calendar/%s", id);
            this.holiday = holiday;
        }
    }
    
}


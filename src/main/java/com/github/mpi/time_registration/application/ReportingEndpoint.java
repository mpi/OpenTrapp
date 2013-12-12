package com.github.mpi.time_registration.application;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.Workload;

@Controller
public class ReportingEndpoint {

    @Autowired
    private WorkLogEntryRepository repository;
    
    @RequestMapping(
            method   = GET, 
            produces = "application/json",
            value    = "/endpoints/v1/projects/{projectName:.+}/work-log/entries")
    @ResponseStatus(OK)
    public @ResponseBody WorkLogJson projectWorkLog(HttpServletResponse response, @PathVariable String projectName){

        List<WorkLogEntryJson> items = new ArrayList<ReportingEndpoint.WorkLogEntryJson>();

        for (WorkLogEntry entry : repository.loadAll()) {
            if(projectName.equals(entry.projectName().toString())){
                items.add(new WorkLogEntryJson(entry.id(), entry.workload(), entry.projectName()));
            }
        };
        
        return new WorkLogJson(items);
    }

    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class WorkLogJson {

        List<WorkLogEntryJson> items;

        WorkLogJson(List<WorkLogEntryJson> items) {
            this.items = items;
        }
    }
    
    @JsonAutoDetect(fieldVisibility=Visibility.ANY)
    class WorkLogEntryJson {

        String link, id, workload, projectName;
        
        WorkLogEntryJson(EntryID id, Workload workload, ProjectName projectName) {
            this.id = id.toString();
            this.workload = workload.toString();
            this.projectName = projectName.toString();
            this.link = String.format("/endpoints/v1/work-log/entries/%s", id);
        }
    }
}

package com.github.mpi.time_registration.application;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLog;
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
    public @ResponseBody WorkLogJson projectWorkLog(@PathVariable String projectName){

        List<WorkLogEntryJson> items = new ArrayList<ReportingEndpoint.WorkLogEntryJson>();

        WorkLog workLog = repository.loadAll().forProject(new ProjectName(projectName));
        
        for (WorkLogEntry entry : workLog) {
            items.add(new WorkLogEntryJson(entry.id(), entry.workload(), entry.projectName(), entry.employee()));
        };
        
        return new WorkLogJson(items);
    }

    @RequestMapping(
            method   = GET, 
            produces = "application/json",
            value    = "/endpoints/v1/employee/{employeeID:.+}/work-log/entries")
    @ResponseStatus(OK)
    public @ResponseBody WorkLogJson employeeWorkLog(@PathVariable String employeeID){
        
        List<WorkLogEntryJson> items = new ArrayList<ReportingEndpoint.WorkLogEntryJson>();
        
        WorkLog workLog = repository.loadAll().forEmployee(new EmployeeID(employeeID));
        
        for (WorkLogEntry entry : workLog) {
            items.add(new WorkLogEntryJson(entry.id(), entry.workload(), entry.projectName(), entry.employee()));
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

        String link, id, workload, projectName, employee;
        
        WorkLogEntryJson(EntryID id, Workload workload, ProjectName projectName, EmployeeID employee) {
            this.id = id.toString();
            this.workload = workload.toString();
            this.projectName = projectName.toString();
            this.employee = employee.toString();
            this.link = String.format("/endpoints/v1/work-log/entries/%s", id);
        }
    }
}

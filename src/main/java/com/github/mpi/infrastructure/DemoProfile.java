package com.github.mpi.infrastructure;

import com.github.mpi.time_registration.domain.*;
import com.github.mpi.time_registration.domain.time.Day;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("demo")
public class DemoProfile {

    @Autowired
    private WorkLogEntryRepository repository;

    @PostConstruct
    void init(){

        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.01"), Workload.of("8h"), new ProjectName("ProjectManhattan"), new EmployeeID("homer.simpson"), Day.of("2014/03/03")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.02"), Workload.of("6h"), new ProjectName("ProjectManhattan"), new EmployeeID("bart.simpson"), Day.of("2014/02/01")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.03"), Workload.of("4h"), new ProjectName("ProjectManhattan"), new EmployeeID("bart.simpson"), Day.of("2014/02/04")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.04"), Workload.of("6h"), new ProjectName("ApolloProgram"), new EmployeeID("homer.simpson"), Day.of("2014/03/12")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.05"), Workload.of("7h 15m"), new ProjectName("ProjectManhattan"), new EmployeeID("marge.simpson"), Day.of("2014/01/17")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.06"), Workload.of("8h"), new ProjectName("ApolloProgram"), new EmployeeID("marge.simpson"), Day.of("2014/03/12")));
        repository.store(new WorkLogEntry(new WorkLogEntry.EntryID("WL.07"), Workload.of("1h 30m"), new ProjectName("ApolloProgram"), new EmployeeID("homer.simpson"), Day.of("2014/03/30")));

    }

}

package com.github.mpi.time_registration.infrastructure;

import com.github.mpi.time_registration.domain.*;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BoundedContext {

    @Autowired
    @Bean
    public RegistrationService registrationService(WorkLogEntryRepository repository, WorkLogEntryFactory factory){
        return new RegistrationService(factory, repository);
    }

    @Autowired
    @Bean
    public UpdateService updateService(WorkLogEntryRepository repository){
        return new UpdateService(repository);
    }
    
    @Autowired
    @Bean
    public WorkLogEntryFactory workLogEntryFactory(EntryIDSequence entryIDSequence, EmployeeContext employeeContext){
        return new WorkLogEntryFactory(entryIDSequence, employeeContext);
    }
    
    @Autowired
    @Bean
    public EntryIDSequence entryIdSequence(){

        return new EntryIDSequence() {


            @Override
            public EntryID nextID() {
                return new EntryID(String.format("WL.%s", UUID.randomUUID()));
            }
        };
    }
    
}

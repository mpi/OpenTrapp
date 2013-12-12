package com.github.mpi.time_registration.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.mpi.time_registration.domain.EntryIDSequence;
import com.github.mpi.time_registration.domain.RegistrationService;
import com.github.mpi.time_registration.domain.UpdateService;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryFactory;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

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
    public WorkLogEntryFactory workLogEntryFactory(EntryIDSequence entryIDSequence){
        return new WorkLogEntryFactory(entryIDSequence);
    }
    
    @Autowired
    @Bean
    public EntryIDSequence entryIdSequence(){

        return new EntryIDSequence() {

            private int nextID = 1;
            
            @Override
            public EntryID nextID() {
                return new EntryID(String.format("WL.%4d", nextID++));
            }
        };
    }
    
}

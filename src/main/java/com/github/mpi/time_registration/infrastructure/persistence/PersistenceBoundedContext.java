package com.github.mpi.time_registration.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

@Component
public class PersistenceBoundedContext {

    private TransientWorkLogEntryRepository repository = new TransientWorkLogEntryRepository();

    @Bean
    @Scope(value="prototype", proxyMode=ScopedProxyMode.INTERFACES)
    public WorkLogEntryRepository repository(){
        return repository;
    }
    
    public void clear(){
        repository = new TransientWorkLogEntryRepository();
    }
}

package com.github.mpi.time_registration.infrastructure.persistence;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

public interface PersistenceBoundedContext {

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.INTERFACES)
    public abstract WorkLogEntryRepository repository();

    public abstract void clear();

}
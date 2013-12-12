package com.github.mpi.time_registration.domain;

public class RegistrationService {

    private final WorkLogEntryFactory factory;
    private final WorkLogEntryRepository repository;

    public RegistrationService(WorkLogEntryFactory factory, WorkLogEntryRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public void submit(String expression) {
        WorkLogEntry newEntry = factory.newEntry(expression);
        repository.store(newEntry);
    }

}

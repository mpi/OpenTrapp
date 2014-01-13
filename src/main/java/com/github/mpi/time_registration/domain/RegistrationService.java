package com.github.mpi.time_registration.domain;

public class RegistrationService {

    private final WorkLogEntryFactory factory;
    private final WorkLogEntryRepository repository;

    public RegistrationService(WorkLogEntryFactory factory, WorkLogEntryRepository repository) {
        this.factory = factory;
        this.repository = repository;
    }

    public void submit(String workload, String projectName, String day) {
        WorkLogEntry newEntry = factory.newEntry(workload, projectName, day);
        repository.store(newEntry);
    }

}

package com.github.mpi.time_registration.infrastructure.persistence.transients;

import org.junit.Before;

import com.github.mpi.time_registration.domain.ProjectNamesContractTest;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;

public class TransientProjectNamesTest extends ProjectNamesContractTest{

    private WorkLogEntryRepository repository = new TransientWorkLogEntryRepository();
    
    @Before
    public void setUp() {

        projectNames = new TransientProjectNames(repository);
    }

    @Override
    protected void followingWorkLogEntriesExist(WorkLogEntry... entries) {
        for (WorkLogEntry entry : entries) {
            repository.store(entry);
        }
    }
    
}

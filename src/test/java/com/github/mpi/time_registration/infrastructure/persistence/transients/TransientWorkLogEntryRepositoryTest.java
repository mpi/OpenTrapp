package com.github.mpi.time_registration.infrastructure.persistence.transients;

import org.junit.Before;

import com.github.mpi.time_registration.domain.WorkLogEntryRepositoryContractTest;
import com.github.mpi.time_registration.infrastructure.persistence.transients.TransientWorkLogEntryRepository;

public class TransientWorkLogEntryRepositoryTest extends WorkLogEntryRepositoryContractTest {

    @Before
    public void setUp() {
        repository = new TransientWorkLogEntryRepository();
    }

}

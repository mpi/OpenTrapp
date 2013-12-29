package com.github.mpi.time_registration.infrastructure.persistence;

import org.junit.Before;

import com.github.mpi.time_registration.domain.WorkLogEntryRepositoryContractTest;

public class TransientWorkLogEntryRepositoryTest extends WorkLogEntryRepositoryContractTest {

    @Before
    public void setUp() {
        repository = new TransientWorkLogEntryRepository();
    }

}

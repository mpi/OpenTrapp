package com.github.mpi.time_registration.infrastructure.persistence;

import org.junit.Before;

import com.github.mpi.time_registration.domain.WorkLogContractTest;


public class TransientWorkLogTest extends WorkLogContractTest {

    @Before
    public void setUp() {
        repository = new TransientWorkLogEntryRepository();
    }
    
}

package com.github.mpi.time_registration.infrastructure.persistence.transients;

import org.junit.Before;

import com.github.mpi.time_registration.domain.WorkLogContractTest;
import com.github.mpi.time_registration.infrastructure.persistence.transients.TransientWorkLogEntryRepository;


public class TransientWorkLogTest extends WorkLogContractTest {

    @Before
    public void setUp() {
        repository = new TransientWorkLogEntryRepository();
    }
    
}

package com.github.mpi.time_registration.domain;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTest {

    @Mock
    private WorkLogEntryRepository repository;
    @Mock
    private WorkLogEntryFactory factory;

    private RegistrationService service;


    @Before
    public void setUp() {

        service = new RegistrationService(factory, repository);
    }
    
    @Test
    public void shouldRegisterNewEntry() throws Exception {

        // given:
        WorkLogEntry newEntry = newEntryFor("1h on #stuff");
        
        // when:
        service.submit("1h on #stuff");
        
        // then:
        verify(repository).store(newEntry);
    }

    // --
    
    private WorkLogEntry aWorkLogEntry() {
        return new WorkLogEntry(new EntryID("id"), Workload.of("1h"), new ProjectName("stuff"), new EmployeeID("homer.simpson"));
    }

    private WorkLogEntry newEntryFor(String expression) {
        WorkLogEntry newWorkLogEntry = aWorkLogEntry();
        when(factory.newEntry(expression)).thenReturn(newWorkLogEntry);
        return newWorkLogEntry;
    }
}

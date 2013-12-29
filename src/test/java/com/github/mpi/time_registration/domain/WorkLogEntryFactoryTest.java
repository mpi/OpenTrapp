package com.github.mpi.time_registration.domain;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;

@RunWith(MockitoJUnitRunner.class)
public class WorkLogEntryFactoryTest {

    @Mock
    private EntryIDSequence entryIDSequence;
    @Mock
    private EmployeeContext employeeContext;

    private WorkLogEntryFactory factory;


    @Before
    public void setUp() {

        factory = new WorkLogEntryFactory(entryIDSequence, employeeContext);
    }
    
    @Test
    public void shouldAssignNextIdFromSequence() throws Exception {

        // given:
        nextIDFromSequenceIs("next-id");
        
        // when:
        WorkLogEntry entry = factory.newEntry("1m on #nothing");
        
        // then:
        assertThat(entry.id()).isEqualTo(new EntryID("next-id"));
    }

    @Test
    public void shouldFailMeaningfullyIfInvalidExpression() throws Exception {
        
        // given:
        
        // when:
        catchException(factory).newEntry("this is invalid");
        
        // then:
        assertThat(caughtException())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid expression: 'this is invalid'");
    }
    
    @Test
    public void shouldParseWorkload() throws Exception {

        // given:
        
        // when:
        WorkLogEntry entry = factory.newEntry("35m #unknown");

        // then:
        assertThat(entry.workload()).isEqualTo(Workload.of("35m"));
    }

    @Test
    public void shouldParseProjectName() throws Exception {
        
        // given:
        
        // when:
        WorkLogEntry entry = factory.newEntry("35m on #Manhattan");
        
        // then:
        assertThat(entry.projectName()).isEqualTo(new ProjectName("Manhattan"));
    }

    @Test
    public void shouldAssignCurrentEmployee() throws Exception {

        // given:
        currentlyLoggedInEmployeeIs("current-employee");
        
        // when:
        WorkLogEntry entry = factory.newEntry("35m on #Manhattan");

        // then:
        assertThat(entry.employee()).isEqualTo(new EmployeeID("current-employee"));
    }
    
    // --
    
    private void currentlyLoggedInEmployeeIs(String id) {
        when(employeeContext.employeeID()).thenReturn(new EmployeeID(id));
    }

    private void nextIDFromSequenceIs(String nextID) {
        when(entryIDSequence.nextID()).thenReturn(new EntryID(nextID));
    }
}

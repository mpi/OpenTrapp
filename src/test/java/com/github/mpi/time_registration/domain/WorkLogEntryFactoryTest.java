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

    private WorkLogEntryFactory factory;

    @Before
    public void setUp() {

        factory = new WorkLogEntryFactory(entryIDSequence);
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
        assertThat(entry.hasSameDataAs(new WorkLogEntry(null, Workload.of("35m"), new ProjectName("unknown")))).isTrue();
    }

    @Test
    public void shouldParseProjectName() throws Exception {
        
        // given:
        
        // when:
        WorkLogEntry entry = factory.newEntry("35m on #Manhattan");
        
        // then:
        assertThat(entry.hasSameDataAs(new WorkLogEntry(null, Workload.of("35m"), new ProjectName("Manhattan")))).isTrue();
    }
    
    // --
    
    private void nextIDFromSequenceIs(String nextID) {
        when(entryIDSequence.nextID()).thenReturn(new EntryID(nextID));
    }
}

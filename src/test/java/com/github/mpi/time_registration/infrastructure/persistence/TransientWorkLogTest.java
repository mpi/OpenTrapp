package com.github.mpi.time_registration.infrastructure.persistence;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLog;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.Workload;

public class TransientWorkLogTest {

    private WorkLogEntryRepository repository;

    @Before
    public void setUp() {
        repository = new TransientWorkLogEntryRepository();
    }
    
    @Test
    public void shouldReturnEmptyWorkLog() throws Exception {

        // given:
        // when:
        WorkLog workLog = repository.loadAll();
        // then:
        assertThat(workLog).isEmpty();
    }

    @Test
    public void shouldReturnWorkLogWithMutipleEntries() throws Exception {
        
        WorkLogEntry entry1 = anEntry("1");
        WorkLogEntry entry2 = anEntry("2");

        // given:
        repositoryContainsFollowingEntries(entry1, entry2);

        // when:
        WorkLog workLog = repository.loadAll();
        
        // then:
        assertThat(workLog).containsExactly(entry1, entry2);
    }

    @Test
    public void shouldReturnWorkLogForGivenProjectOnly() throws Exception {
        
        WorkLogEntry entry1 = anEntryWithProject("1", "ManhattanProject");
        WorkLogEntry entry2 = anEntryWithProject("2", "ApolloProgram");
        
        // given:
        repositoryContainsFollowingEntries(entry1, entry2);
        
        // when:
        WorkLog workLog = repository.loadAll().forProject(new ProjectName("ManhattanProject"));
        
        // then:
        assertThat(workLog).containsExactly(entry1);
    }

    // --
    
    private void repositoryContainsFollowingEntries(WorkLogEntry... entries){
        for (WorkLogEntry entry : entries) {
            repository.store(entry);
        }
    }
    
    private WorkLogEntry anEntry(String id) {
        return new WorkLogEntry(new EntryID(id), Workload.of("50m"), new ProjectName("projectA"));
    }
    
    private WorkLogEntry anEntryWithProject(String id, String projectName) {
        return new WorkLogEntry(new EntryID(id), Workload.of("50m"), new ProjectName(projectName));
    }
    
}

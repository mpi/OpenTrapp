package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLog;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.Workload;

public abstract class WorkLogContractTest {

    protected WorkLogEntryRepository repository;

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
        
        WorkLogEntry relevantEntry = anEntryWithProject("1", "ManhattanProject");
        WorkLogEntry irrelevantEntry = anEntryWithProject("2", "ApolloProgram");
        
        // given:
        repositoryContainsFollowingEntries(relevantEntry, irrelevantEntry);
        
        // when:
        WorkLog workLog = repository.loadAll().forProject(new ProjectName("ManhattanProject"));
        
        // then:
        assertThat(workLog).containsExactly(relevantEntry);
    }

    @Test
    public void shouldReturnWorkLogForGivenEmployeeOnly() throws Exception {
        
        WorkLogEntry irrelevatntEntry = anEntryForEmployee("1", "Bart");
        WorkLogEntry relevantEntry = anEntryForEmployee("2", "Homer");
        
        // given:
        repositoryContainsFollowingEntries(irrelevatntEntry, relevantEntry);
        
        // when:
        WorkLog workLog = repository.loadAll().forEmployee(new EmployeeID("Homer"));
        
        // then:
        assertThat(workLog).containsExactly(relevantEntry);
    }

    @Test
    public void shouldReturnWorkLogForGivenEmployeeAndProjectOnly() throws Exception {
        
        WorkLogEntry irrelevatntEntry1 = anEntryForEmployeeAndProject("1", "Bart", "X");
        WorkLogEntry irrelevatntEntry2 = anEntryForEmployeeAndProject("2", "Homer", "X");
        WorkLogEntry irrelevatntEntry3 = anEntryForEmployeeAndProject("3", "Bart", "Y");
        WorkLogEntry relevantEntry     = anEntryForEmployeeAndProject("4", "Homer", "Y");

        // given:
        repositoryContainsFollowingEntries(irrelevatntEntry1, irrelevatntEntry2, irrelevatntEntry3, relevantEntry);
        
        // when:
        WorkLog workLog = repository.loadAll()
                .forEmployee(new EmployeeID("Homer"))
                .forProject(new ProjectName("Y"));
        
        // then:
        assertThat(workLog).containsExactly(relevantEntry);
    }
    
    // --
    
    private void repositoryContainsFollowingEntries(WorkLogEntry... entries) {
        for (WorkLogEntry entry : entries) {
            repository.store(entry);
        }
    }

    private WorkLogEntry anEntryForEmployee(String id, String employee) {
        return new WorkLogEntry(new EntryID(id), Workload.of("50m"), new ProjectName("projectA"), new EmployeeID(employee));
    }

    private WorkLogEntry anEntryForEmployeeAndProject(String id, String employee, String projectName) {
        return new WorkLogEntry(new EntryID(id), Workload.of("50m"), new ProjectName(projectName), new EmployeeID(employee));
    }

    private WorkLogEntry anEntryWithProject(String id, String projectName) {
        WorkLogEntry entry = anEntry(id);
        entry.changeProjectTo(new ProjectName(projectName));
        return entry;
    }

    private WorkLogEntry anEntry(String id) {
        return new WorkLogEntry(new EntryID(id), Workload.of("50m"), new ProjectName("projectA"), new EmployeeID("homer.simpson"));
    }

}
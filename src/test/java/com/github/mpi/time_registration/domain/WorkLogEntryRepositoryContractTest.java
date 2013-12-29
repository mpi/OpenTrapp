package com.github.mpi.time_registration.domain;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.mpi.time_registration.domain.EmployeeID;
import com.github.mpi.time_registration.domain.ProjectName;
import com.github.mpi.time_registration.domain.WorkLog;
import com.github.mpi.time_registration.domain.WorkLogEntry;
import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository.WorkLogEntryAlreadyExists;
import com.github.mpi.time_registration.domain.WorkLogEntryRepository.WorkLogEntryDoesNotExists;
import com.github.mpi.time_registration.domain.Workload;

public abstract class WorkLogEntryRepositoryContractTest {

    protected WorkLogEntryRepository repository;

    @Test
    public void shouldBeEmptyOnStart() throws Exception {
    
        // given:
        // when:
        WorkLog log = repository.loadAll();
    
        // then:
        assertThat(log).isEmpty();
    }

    @Test
    public void shouldStoreAndLoadEntry() throws Exception {
    
        // given:
        EntryID id = entryID("entry-id");
        WorkLogEntry entry = new WorkLogEntry(id, Workload.of("25m"), new ProjectName("Manhattan"), new EmployeeID("homer.simpson"));
    
        // when:
        repository.store(entry);
    
        // then:
        assertThat(reflectionEquals(entry, repository.load(id))).isTrue();
    }

    @Test
    public void shouldFailMeaningfullyIfEntryNotFound() throws Exception {
    
        // given:
        // when:
        catchException(repository).load(entryID("404"));
    
        // then:
        assertThat(caughtException())
            .isInstanceOf(WorkLogEntryDoesNotExists.class)
            .hasMessage("WorkLogEntry with id='404' does not exists!");
    }

    @Test
    public void shouldFailMeaningfullyIfTryingToStoreEntryThatAlreadyExists() throws Exception {
    
        // given:
        entryWithGivenIdAlreadyExists(entryID("already-taken"));
    
        // when:
        WorkLogEntry entry = newEntryWithId(entryID("already-taken"));
        catchException(repository).store(entry);
    
        // then:
        assertThat(caughtException())
            .isInstanceOf(WorkLogEntryAlreadyExists.class)
            .hasMessage("WorkLogEntry with id='already-taken' already exists!");
    }

    // --
    
    private void entryWithGivenIdAlreadyExists(EntryID id) {
        repository.store(newEntryWithId(id));
    }

    private WorkLogEntry newEntryWithId(EntryID id) {
        return new WorkLogEntry(id, Workload.of("66h"), new ProjectName("doesn't matter"), new EmployeeID("homer.simpson"));
    }

    private EntryID entryID(String id) {
        return new EntryID(id);
    }

}
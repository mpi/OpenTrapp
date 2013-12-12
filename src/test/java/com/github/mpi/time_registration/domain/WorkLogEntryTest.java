package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;

public class WorkLogEntryTest {

    @Test
    public void shouldUpdateWorkload() throws Exception {

        // given:
        WorkLogEntry entry = new WorkLogEntry(null, Workload.of("10h"), null);
        
        // when:
        entry.updateWorkload(Workload.of("12h"));
        
        // then:
        assertThat(entry.workload()).isEqualTo(Workload.of("12h"));
    }
   
    @Test
    public void shouldChangeProject() throws Exception {
        
        // given:
        WorkLogEntry entry = new WorkLogEntry(null, null, new ProjectName("OldProject"));
        
        // when:
        entry.changeProjectTo(new ProjectName("NewProject"));
        
        // then:
        assertThat(entry.projectName()).isEqualTo(new ProjectName("NewProject"));
    }
    
    @Test
    public void shouldEntriesWithEqualData() throws Exception {

        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project"));
        WorkLogEntry same = new WorkLogEntry(new EntryID("other-id"), Workload.of("30m"), project("project"));
        
        // when:
        boolean areEqual = some.hasSameDataAs(same);
        
        // then:
        assertThat(areEqual).isTrue();
    }

    @Test
    public void shouldDifferentEntriesNotBeEqual_Workload() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project"));
        WorkLogEntry other = new WorkLogEntry(new EntryID("other-id"), Workload.of("29m"), project("project"));
        
        // when:
        boolean areEqual = some.hasSameDataAs(other);
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldDifferentEntriesNotBeEqual_ProjectName() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project"));
        WorkLogEntry other = new WorkLogEntry(new EntryID("other-id"), Workload.of("30m"), project("meeting"));
        
        // when:
        boolean areEqual = some.hasSameDataAs(other);
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldNotBeEqualToSomethigDifferentThanWorkLogEntry() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project"));
        
        // when:
        boolean areEqual = some.equals("30m on #project");
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldBeEqualIfHaveEqualIDs() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project-A"));
        WorkLogEntry same = new WorkLogEntry(new EntryID("some-id"), Workload.of("35m"), project("project-B"));
        
        // when:
        boolean areEqual = some.equals(same);
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldNotBeEqualIfHaveDifferentIDs() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("35m"), project("project-A"));
        WorkLogEntry other = new WorkLogEntry(new EntryID("other-id"), Workload.of("35m"), project("project-A"));
        
        // when:
        boolean areEqual = some.equals(other);
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldHaveEqualHashCodesIfHaveEqualIDs() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("30m"), project("project-A"));
        WorkLogEntry same = new WorkLogEntry(new EntryID("some-id"), Workload.of("35m"), project("project-B"));
        
        // when:
        boolean areEqual = some.hashCode() == same.hashCode();
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldNotHaveEqualHashCodesIfHaveDifferentIDs() throws Exception {
        
        // given:
        WorkLogEntry some = new WorkLogEntry(new EntryID("some-id"), Workload.of("35m"), project("project-A"));
        WorkLogEntry other = new WorkLogEntry(new EntryID("other-id"), Workload.of("35m"), project("project-A"));
        
        // when:
        boolean areEqual = some.hashCode() == other.hashCode();
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    // --
    
    private ProjectName project(String name) {
        return new ProjectName(name);
    }
    
}

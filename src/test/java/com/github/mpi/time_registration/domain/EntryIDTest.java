package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.mpi.time_registration.domain.WorkLogEntry.EntryID;

public class EntryIDTest {

    @Test
    public void shouldHaveDescriptiveToString() throws Exception {

        // given:
        // when:
        EntryID entryID = new EntryID("entry-id");
        // then:
        assertThat(entryID.toString()).isEqualTo("entry-id");
    }
    
    @Test
    public void shouldSameIDsBeEqual() throws Exception {

        // given:
        EntryID some = new EntryID("entry-id");
        EntryID same = new EntryID("entry-id");
        
        // when:
        boolean areEqual = some.equals(same);
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldDifferentIDsNotBeEqual() throws Exception {
        
        // given:
        EntryID some = new EntryID("entry-A");
        EntryID other = new EntryID("entry-B");
        
        // when:
        boolean areEqual = some.equals(other);
        
        // then:
        assertThat(areEqual).isFalse();
    }

    @Test
    public void shouldNotBeEqualToSomethingOtherThanID() throws Exception {
        
        // given:
        EntryID some = new EntryID("entry-id");
        
        // when:
        boolean areEqual = some.equals("entry-id");
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldSameIDsHaveSameHashCodes() throws Exception {
        
        // given:
        EntryID some = new EntryID("entry-id");
        EntryID same = new EntryID("entry-id");
        
        // when:
        boolean areEqual = some.hashCode() == same.hashCode();
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldDifferentIDsHaveDifferentHashCodes() throws Exception {
        
        // given:
        EntryID some = new EntryID("entry-A");
        EntryID other = new EntryID("entry-B");
        
        // when:
        boolean areEqual = some.hashCode() == other.hashCode();
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
}

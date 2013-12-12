package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.github.mpi.time_registration.domain.ProjectName;

public class ProjectNameTest {

    @Test
    public void shouldSameProjectNamesBeEqual() throws Exception {

        // given:
        ProjectName some = new ProjectName("project");
        ProjectName same = new ProjectName("project");
        
        // when:
        boolean areEqual = some.equals(same);
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldDifferentProjectNamesNotBeEqual() throws Exception {
        
        // given:
        ProjectName some = new ProjectName("projectA");
        ProjectName other = new ProjectName("projectB");
        
        // when:
        boolean areEqual = some.equals(other);
        
        // then:
        assertThat(areEqual).isFalse();
    }

    @Test
    public void shouldNotBeEqualToSomethingOtherThanProject() throws Exception {
        
        // given:
        ProjectName some = new ProjectName("project");
        
        // when:
        boolean areEqual = some.equals("project");
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldSameProjectNamesHaveSameHashCodes() throws Exception {
        
        // given:
        ProjectName some = new ProjectName("project");
        ProjectName same = new ProjectName("project");
        
        // when:
        boolean areEqual = some.hashCode() == same.hashCode();
        
        // then:
        assertThat(areEqual).isTrue();
    }
    
    @Test
    public void shouldDifferentProjectNamesHaveDifferentHashCodes() throws Exception {
        
        // given:
        ProjectName some = new ProjectName("projectA");
        ProjectName other = new ProjectName("projectB");
        
        // when:
        boolean areEqual = some.hashCode() == other.hashCode();
        
        // then:
        assertThat(areEqual).isFalse();
    }
    
    @Test
    public void shouldHaveDescriptiveToString() throws Exception {

        // given:
        ProjectName some = new ProjectName("projectManhattan");
        // when:
        String representation = some.toString();
        // then:
        assertThat(representation).isEqualTo("projectManhattan");
    }
    
}

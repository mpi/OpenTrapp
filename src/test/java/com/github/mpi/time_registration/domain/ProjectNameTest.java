package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.github.mpi.time_registration.domain.ProjectName;

public class ProjectNameTest extends ValueObjectContractTest {

    protected ProjectName aValue() {
        return new ProjectName("project");
    }

    protected ProjectName equalValue() {
        return new ProjectName("project");
    }
    
    protected ProjectName differentValue() {
        return new ProjectName("differentProject");
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

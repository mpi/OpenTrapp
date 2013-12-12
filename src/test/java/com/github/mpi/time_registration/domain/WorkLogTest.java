package com.github.mpi.time_registration.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.github.mpi.time_registration.domain.WorkLog;

public class WorkLogTest {

    @Test
    public void shouldStartWithEmptyWorkLog() throws Exception {

        // given:
        // when:
        WorkLog workLog = new WorkLog();
        
        // then:
        assertThat(workLog).isEmpty();
    }
    
}

package com.github.mpi.users_and_access.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import com.github.mpi.time_registration.domain.ValueObjectContractTest;

public class UserTest extends ValueObjectContractTest{

    @Override
    protected Object aValue() {
        return new User("homer.simpson@springfield.us", "Homer Simpson");
    }

    @Override
    protected Object equalValue() {
        return new User("homer.simpson@springfield.us", "homer");
    }

    @Override
    protected Object differentValue() {
        return new User("bart.simpson@springfield.us", "Bart Simpson");
    }

    @Test
    public void shouldHaveDescriptiveStringRepresentation() throws Exception {

        // given:
        User homer = new User("homer.simpson@springfield.us", "Homer Simpson");
        
        // when:
        String stringRepresentation = homer.toString();
        
        // then:
        assertThat(stringRepresentation)
            .contains("homer.simpson@springfield.us")
            .contains("Homer Simpson");
    }
    
}

package com.github.mpi.time_registration.domain.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

import org.junit.Test;

import com.github.mpi.time_registration.domain.ValueObjectContractTest;

public class DayTest extends ValueObjectContractTest{

    @Test
    public void shouldParseValidDate() throws Exception {

        // given:
        // when:
        Day day = Day.of("2012/01/01");
        // then:
        assertThat(day).isNotNull();
    }

    @Test
    public void shouldFailMeaningfullyOnInvalidDate() throws Exception {
        
        // given:
        try{
            // when:
            Day.of("2014/02/29");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch(Exception e){
            // then:
            assertThat(e)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid date: 2014/02/29");
        }
    }

    @Test
    public void shouldFailMeaningfullyOnInvalidDateFormat() throws Exception {
        
        // given:
        try{
            // when:
            Day.of("01/01/2013");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch(Exception e){
            // then:
            assertThat(e)
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid date format: 01/01/2013. Expected format is: yyyy/mm/dd");
        }
    }

    @Test
    public void shouldHaveDescriptiveToString() throws Exception {
        
        // given:
        // when:
        Day day = Day.of("2012/01/01");
        // then:
        assertThat(day.toString()).isEqualTo("2012/01/01");
    }
    
    @Override
    protected Day aValue() {
        return Day.of("2013/11/05");
    }

    @Override
    protected Object equalValue() {
        return Day.of("2013/11/05");
    }

    @Override
    protected Object differentValue() {
        return Day.of("2012/07/04");
    }
    
}

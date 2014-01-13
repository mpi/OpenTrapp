package support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.mpi.time_registration.infrastructure.BoundedContext;

public class ContextTest {

    private EmbeddedServer server = new EmbeddedServer(9090);

    @Before
    public void setUp() {
        server.start();
    }
    
    @After
    public void tearDown(){
        server.stop();
    }
    
    @Test
    public void shouldEstablishContext() throws Exception {

        // given:
        AutowireCapableBeanFactory beanFactory = beanFactory();

        // when:
        Object bean = beanFactory.getBean(BoundedContext.class);
        
        // then:
        assertThat(bean).isNotNull();
        
    }

    // --
    
    private AutowireCapableBeanFactory beanFactory() {
        AbstractApplicationContext adHocAutowiringContext = new AnnotationConfigApplicationContext(AutowiredAnnotationBeanPostProcessor.class);
        adHocAutowiringContext.setParent(Context.get(server.server));
        AutowireCapableBeanFactory beanFactory = adHocAutowiringContext.getAutowireCapableBeanFactory();
        return beanFactory;
    }
    
}

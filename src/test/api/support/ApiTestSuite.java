package support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.github.mpi.time_registration.infrastructure.BoundedContext;

import concordion.ConcordionFixture;

@RunWith(Suite.class)
@SuiteClasses(ConcordionFixture.class)
public class ApiTestSuite {
    
    private static EmbeddedServer server;

    @BeforeClass
    public static void setUp() {
        server = new EmbeddedServer(8080);
        server.start();
        
        assertThatContextHasBeenSuccessfulyEstablished();
    }

    private static void assertThatContextHasBeenSuccessfulyEstablished() {
        
        // run acceptance tests only if context has been successfuly created
        
        Object bean = adHocInjector().getBean(BoundedContext.class);
        assertThat(bean).isNotNull();
    }

    private static AutowireCapableBeanFactory adHocInjector() {
        AbstractApplicationContext adHocAutowiringContext 
            = new AnnotationConfigApplicationContext(AutowiredAnnotationBeanPostProcessor.class);
        adHocAutowiringContext.setParent(ApiTestSuite.context());
        return adHocAutowiringContext.getAutowireCapableBeanFactory();
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
 
    public static ApplicationContext context() {
        return Context.get(server.server);
    }
}
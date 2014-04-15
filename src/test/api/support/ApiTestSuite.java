package support;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.context.ApplicationContext;

import concordion.ConcordionFixture;

@RunWith(Suite.class)
@SuiteClasses(ConcordionFixture.class)
public class ApiTestSuite {
    
    private static EmbeddedServer server;

    @BeforeClass
    public static void setUp() {
        server = new EmbeddedServer(8080);
        server.start();
    }

    @AfterClass
    public static void tearDown() {
        server.stop();
    }
 
    public static ApplicationContext context() {
        return Context.get(server.server);
    }
}
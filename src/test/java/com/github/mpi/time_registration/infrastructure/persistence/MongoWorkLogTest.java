package com.github.mpi.time_registration.infrastructure.persistence;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.mpi.infrastructure.DevelopmentProfile;
import com.github.mpi.infrastructure.MongoContext;
import com.github.mpi.infrastructure.MongoLabProfile;
import com.github.mpi.time_registration.domain.WorkLogContractTest;
import com.github.mpi.time_registration.domain.WorkLogEntry;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
        MongoContext.class, 
        MongoLabProfile.class, 
        DevelopmentProfile.class, 
        MongoDevelopmentDatabase.class
})
@ActiveProfiles({"mongo", "development"})
public class MongoWorkLogTest extends WorkLogContractTest{

    @Autowired
    private MongoTemplate mongo;
    
    @Before
    public void setUp() {
        repository = new MongoWorkLogEntryRepository(mongo);
        mongo.dropCollection(WorkLogEntry.class);
    }
}

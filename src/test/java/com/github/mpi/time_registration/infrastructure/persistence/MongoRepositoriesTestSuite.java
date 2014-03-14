package com.github.mpi.time_registration.infrastructure.persistence;
import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@RunWith(Suite.class)
@SuiteClasses({
    MongoWorkLogEntryRepositoryTest.class
})
public class MongoRepositoriesTestSuite {

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    @BeforeClass
    public static void startDb() throws IOException {

        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.Main.DEVELOPMENT, 27017, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
    }
    
    @AfterClass
    public static void stopDb() throws Exception {

        mongod.stop();
        mongodExe.stop();
    }
}
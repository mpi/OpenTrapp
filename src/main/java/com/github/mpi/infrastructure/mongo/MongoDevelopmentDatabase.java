package com.github.mpi.infrastructure.mongo;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Component
@Profile("mongo-dev")
public class MongoDevelopmentDatabase {

    private static MongodExecutable mongodExe;
    private static MongodProcess mongod;

    @PostConstruct
    public static void startDb() throws IOException {
        MongodStarter runtime = MongodStarter.getDefaultInstance();
        mongodExe = runtime.prepare(new MongodConfig(Version.Main.DEVELOPMENT, 27017, Network.localhostIsIPv6()));
        mongod = mongodExe.start();
        System.err.println("----------------- Development database started!");
    }
    
    @PreDestroy
    public static void stopDb() throws Exception {
        mongod.stop();
        mongodExe.stop();
        System.err.println("----------------- Development database stopped!");
    }
}
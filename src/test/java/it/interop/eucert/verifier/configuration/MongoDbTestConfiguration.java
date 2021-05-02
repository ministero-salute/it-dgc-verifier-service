package it.interop.eucert.verifier.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@Configuration
public class MongoDbTestConfiguration {


    private static final String IP = "localhost";
    private static final int PORT = 28017; 
    
    @Bean
    public IMongodConfig embeddedMongoConfiguration() throws IOException {
        return new MongodConfigBuilder()
                .version(Version.LATEST_NIGHTLY)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();
    }
}
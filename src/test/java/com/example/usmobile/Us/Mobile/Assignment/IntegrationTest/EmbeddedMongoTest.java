package com.example.usmobile.Us.Mobile.Assignment.IntegrationTest;
import com.example.usmobile.Us.Mobile.Assignment.model.User;
import com.example.usmobile.Us.Mobile.Assignment.repository.UserRepository;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmbeddedMongoTest {

    private static MongodExecutable mongodExecutable;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeAll
    static void setUp() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        int port = 27017;
        ImmutableMongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new de.flapdoodle.embed.mongo.config.Net(port, Network.localhostIsIPv6()))
                .build();
        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();
    }

    @AfterAll
    static void tearDown() {
        if (mongodExecutable != null) {
            mongodExecutable.stop();
        }
    }

    @Test
    void testInsertUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setMdn("1234567890");

        user = userRepository.save(user);
        assertNotNull(user.getId());

        Optional<User> dbuser = userRepository.findUserByEmail("john.doe@example.com");
        if(dbuser.isPresent()){
            User retrievedUser = dbuser.get();
            assertNotNull(dbuser);
            assertEquals("John", retrievedUser.getFirstName());
            assertEquals("Doe", retrievedUser.getLastName());
            assertEquals("1234567890", retrievedUser.getMdn());
        }

    }
}


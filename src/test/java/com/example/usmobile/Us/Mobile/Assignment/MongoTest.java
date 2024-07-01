package com.example.usmobile.Us.Mobile.Assignment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

//    @Test
    public void testMongoConnection() {
        assertThat(mongoTemplate.getDb().getName()).isEqualTo("mobile");
    }
}

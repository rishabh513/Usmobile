package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByMdn(String mdn);
    boolean existsByMdn(String userid);
}

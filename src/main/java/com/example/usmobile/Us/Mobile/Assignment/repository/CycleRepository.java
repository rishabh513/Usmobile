package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CycleRepository extends MongoRepository<Cycle, String> , CustomCycleRepository {


     List<Cycle> findByUserIdAndMdn(String userId, String mdn);
}

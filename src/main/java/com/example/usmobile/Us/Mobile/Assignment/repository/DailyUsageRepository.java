package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface DailyUsageRepository extends MongoRepository<DailyUsage, String> , CustomDailyUsageRepository{

    DailyUsage findByUsageDate(LocalDateTime localDateTime);
}

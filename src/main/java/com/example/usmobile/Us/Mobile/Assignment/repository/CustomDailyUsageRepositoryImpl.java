package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class CustomDailyUsageRepositoryImpl implements CustomDailyUsageRepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public DailyUsage getDailyUsageByDate(LocalDateTime date) {
        //
        return new DailyUsage();
    }

    @Override
    public List<DailyUsage> getDailyUsageByDateRange(String userId, String mdn,LocalDateTime start,
                                                     LocalDateTime end) {

        return
               mongoTemplate.find(Query.query(
                Criteria.where("userId").is(userId)
                        .and("mdn").is(mdn)
                        .and("usageDate").gte(start).lte(end)

        ), DailyUsage.class);

    }

}

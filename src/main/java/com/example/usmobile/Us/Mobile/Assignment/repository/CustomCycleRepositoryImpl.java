package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class CustomCycleRepositoryImpl implements CustomCycleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Cycle findcycleByCriteria(String userId, String mdn, LocalDate today) {

       return  mongoTemplate.findOne(Query.query(
                       Criteria.where("userId").is(userId)
                               .and("mdn").is(mdn)
                               .and("startDate").lte(today)
                               .and("endDate").gte(today)
        ), Cycle.class);

    }

}

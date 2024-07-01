package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;

import java.time.LocalDate;

public interface CustomCycleRepository {

    Cycle findcycleByCriteria(String userId, String mdn, LocalDate today);

}

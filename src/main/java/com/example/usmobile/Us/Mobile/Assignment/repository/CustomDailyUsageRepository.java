package com.example.usmobile.Us.Mobile.Assignment.repository;

import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomDailyUsageRepository {

    DailyUsage getDailyUsageByDate(LocalDateTime date);
    List<DailyUsage> getDailyUsageByDateRange(String userId, String mdn,LocalDateTime start, LocalDateTime end);
}

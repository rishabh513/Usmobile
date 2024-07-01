package com.example.usmobile.Us.Mobile.Assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyUsageResponse {

    private LocalDate usageDate;
    private DailyUsage dailyUsage;
}

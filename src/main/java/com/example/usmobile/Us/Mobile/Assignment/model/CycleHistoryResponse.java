package com.example.usmobile.Us.Mobile.Assignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class CycleHistoryResponse {

    LocalDate startDate;
    LocalDate endDate;
    String cycleId;

}

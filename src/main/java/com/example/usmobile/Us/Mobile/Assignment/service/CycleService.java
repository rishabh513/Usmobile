package com.example.usmobile.Us.Mobile.Assignment.service;

import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;
import com.example.usmobile.Us.Mobile.Assignment.model.CycleHistoryResponse;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsageResponse;
import com.example.usmobile.Us.Mobile.Assignment.repository.CycleRepository;
import com.example.usmobile.Us.Mobile.Assignment.repository.DailyUsageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CycleService {

    @Autowired
    private CycleRepository cycleRepository;
    @Autowired
    private DailyUsageRepository dailyUsageRepository;
    @Autowired
    private Validator validator;

    @Autowired
    UserService userService;

    @Autowired
    DailyUsageService dailyUsageService;


    public Cycle createCycle(String userId, String mdn) throws CustomException {
        userService.getUser(userId,mdn);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        //Check if the cycle is created for the user
        Cycle cycle = cycleRepository.findcycleByCriteria(userId,mdn,startDate);
        if (cycle != null) {
            throw new CustomException("cycle already exist");
        }

        Cycle cycleNew = Cycle.builder().mdn(mdn).userId(userId).startDate(startDate).endDate(endDate).build();
        return cycleRepository.insert(cycleNew);
    }

    public Cycle endCycle(String cycleId) throws CustomException {
        Cycle cycle = cycleRepository.findById(cycleId).orElse(null);
        if (cycle == null) {
            throw new CustomException("cycle not found");
        }
        LocalDate endDate = LocalDate.now();
        return cycleRepository.save(cycle);
        
    }

    public List<DailyUsageResponse> getCurrentCycleDailyUsage(String userId, String mdn) throws CustomException {

        //step1 - we are going to check if the user is present
        userService.getUser(userId,mdn);
        // step2 - Get the current cycle based on today's date
        LocalDate currentDate = LocalDate.now();
        //step3 - get the daily usage for dates between cycle start date and cycle today's date
        Cycle cycle = cycleRepository.findcycleByCriteria(userId,mdn,currentDate);
        if (cycle == null) {
            throw new CustomException("cycle not found");
        }
        List<DailyUsage> usageForGivenCycle = dailyUsageService.getUsageBetweenDates(userId,mdn,cycle.getStartDate(), cycle.getEndDate());
        // Map query result to response object only used MB
        return usageForGivenCycle.stream().map(arr -> DailyUsageResponse.builder()
                .usageDate(arr.getUsageDate().toLocalDate()).dailyUsage(arr).build()).collect(Collectors.toList());
    }


    public List<CycleHistoryResponse> getCycleHistoryByMdn(String userId, String mdn) {

        List<Cycle> cycles = cycleRepository.findByUserIdAndMdn(userId,mdn);
        return cycles.stream().map(c -> CycleHistoryResponse.builder()
                .cycleId(c.getId()).startDate(c.getStartDate()).endDate(c.getEndDate()).build()).collect(Collectors.toList());
    }


}

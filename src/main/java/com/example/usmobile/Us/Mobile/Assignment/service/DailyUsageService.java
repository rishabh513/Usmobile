package com.example.usmobile.Us.Mobile.Assignment.service;

import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import com.example.usmobile.Us.Mobile.Assignment.repository.DailyUsageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DailyUsageService {

    @Autowired
    DailyUsageRepository dailyUsageRepository;




    public DailyUsage createDailyUsage(String userId, String mdn) throws CustomException {

        DailyUsage check = dailyUsageRepository.findByUsageDate(LocalDateTime.now().toLocalDate().atStartOfDay());
        if(check != null){
            throw new CustomException("Daily Usage already tracked for the day. Please use PUT method to update usage");
        }
        DailyUsage dailyUsage = DailyUsage.builder().usageDate(LocalDateTime.now().toLocalDate().atStartOfDay())
                .userId(userId).mdn(mdn).usedInMb(0.0)
                .build();
        return dailyUsageRepository.save(dailyUsage);
    }

    public DailyUsage updateUsage(String id, double usedInMb) throws CustomException {

        Optional<DailyUsage> opt = dailyUsageRepository.findById(id);
        if(opt.isPresent()){
            DailyUsage dailyUsage = opt.get();
            dailyUsage.setUsedInMb(dailyUsage.getUsedInMb() + usedInMb);
//            dailyUsage.setUpdateAt(LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
            return dailyUsageRepository.save(dailyUsage);

        } else{
            throw new CustomException("Invalid usage");
        }
        //If we are updating in the same day, it is allowed
        //we set the date to locaDateTime now
        //else we throw exception we cannot update for yesterday's usage
    }

    public List<DailyUsage> getUsageBetweenDates(String userId, String mdn,LocalDate startDate, LocalDate endDate) {

        return dailyUsageRepository.getDailyUsageByDateRange(userId, mdn,startDate.atStartOfDay(),endDate.atTime(LocalTime.MAX));
    }


}

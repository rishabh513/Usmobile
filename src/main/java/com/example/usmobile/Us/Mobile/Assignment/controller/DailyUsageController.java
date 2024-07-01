package com.example.usmobile.Us.Mobile.Assignment.controller;

import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import com.example.usmobile.Us.Mobile.Assignment.repository.DailyUsageRepository;
import com.example.usmobile.Us.Mobile.Assignment.service.DailyUsageService;
import com.example.usmobile.Us.Mobile.Assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DailyUsageController {


    @Autowired
    Validator validator;
    @Autowired
    DailyUsageRepository dailyUsageRepository;

    @Autowired
    DailyUsageService dailyUsageService;

    @Autowired
    UserService userService;

    /**
     *
     * @param userId
     * @param mdn
     * @return
     * @throws CustomException
     */
    @PostMapping("/create-daily-usage")
   public DailyUsage createDailyUsage(@RequestParam String userId, @RequestParam String mdn) throws CustomException {

        validator.validateId(userId);
        validator.validatePhoneNumber(mdn);
        userService.getUser(userId,mdn);
       return  dailyUsageService.createDailyUsage(userId,mdn);

    }

    /**  Let's assume there's a service that tracks usage, and it calls this endpoint to update the usage information.
     *   Ideally, this endpoint would be subscribed to a topic or stream.
     * This method updates usage for a given user
     * @param id
     * @param usedInMb
     * @return
     * @throws CustomException
     */
    @PutMapping("/create-daily-usage/{id}")
    public DailyUsage updateDailyUsage(@PathVariable String id,
                                        @RequestParam double usedInMb) throws CustomException {
        validator.validateId(id);
        return dailyUsageService.updateUsage(id, usedInMb);
    }

}

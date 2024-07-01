package com.example.usmobile.Us.Mobile.Assignment.controller;

import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;
import com.example.usmobile.Us.Mobile.Assignment.model.CycleHistoryResponse;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsageResponse;
import com.example.usmobile.Us.Mobile.Assignment.service.CycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class CycleController {

    @Autowired
    private CycleService cycleService;

    @Autowired
    private Validator validator;

    /**
     *
     * @param userId
     * @param mdn
     * @return
     * @throws CustomException
     */
    @GetMapping("/current-daily-usage")
    public ResponseEntity<List<DailyUsageResponse>> getCurrentCycleDailyUsage(
            @RequestParam String userId,
            @RequestParam String mdn) throws CustomException {
        validator.validateId(userId);
        validator.validatePhoneNumber(mdn);
        return ResponseEntity.status(HttpStatus.OK).body(cycleService.getCurrentCycleDailyUsage(userId, mdn));
    }

    /**
     *
     * @param userId
     * @param mdn
     * @return
     * @throws CustomException
     */
    @GetMapping("/cycle-history")
    public ResponseEntity<List<CycleHistoryResponse>> getCycleHistory(
            @RequestParam String userId,
            @RequestParam String mdn) throws CustomException {
        validator.validateId(userId);
        validator.validatePhoneNumber(mdn);
        return ResponseEntity.status(HttpStatus.OK).body(cycleService.getCycleHistoryByMdn(userId, mdn));
    }

    /**
     *
     * @param userId
     * @param mdn
     * @return
     * @throws CustomException
     */
    @PostMapping("/cycle")
    public ResponseEntity<Cycle> createCycle(@RequestParam String userId,
                                             @RequestParam String mdn)throws CustomException {
        validator.validateId(userId);
        validator.validatePhoneNumber(mdn);
        return ResponseEntity.ok(cycleService.createCycle(userId,mdn)) ;
    }


}

package com.example.usmobile.Us.Mobile.Assignment.controller;

import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.Cycle;
import com.example.usmobile.Us.Mobile.Assignment.model.CycleHistoryResponse;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsageResponse;
import com.example.usmobile.Us.Mobile.Assignment.service.CycleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CycleControllerTest {

    @Mock
    private CycleService cycleService;

    @Mock
    private Validator validator;

    @InjectMocks
    private CycleController cycleController;

    private List<DailyUsageResponse> dailyUsageResponseList;
    private List<CycleHistoryResponse> cycleHistoryResponseList;
    private Cycle cycle;

    @BeforeEach
    public void setUp() {
        dailyUsageResponseList = new ArrayList<>();
        cycleHistoryResponseList = new ArrayList<>();
        cycle = new Cycle();
    }

    @Test
    public void testGetCurrentCycleDailyUsage_Valid() throws CustomException {

        dailyUsageResponseList.add(DailyUsageResponse.builder().
                usageDate(LocalDate.now()).dailyUsage(DailyUsage.builder()
                                .usedInMb(12.87).mdn("1234567890")
                                .usageDate(LocalDateTime.now()).userId("1")
                                .build()).build());
        when(cycleService.getCurrentCycleDailyUsage(anyString(), anyString())).thenReturn(dailyUsageResponseList);

        ResponseEntity<List<DailyUsageResponse>> response = cycleController.getCurrentCycleDailyUsage("validUserId", "validMdn");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dailyUsageResponseList, response.getBody());
    }

    @Test
    public void testGetCurrentCycleDailyUsage_InvalidUserId() throws CustomException {
        doThrow(new CustomException("Invalid userId")).when(validator).validateId(anyString());

        try {
            ResponseEntity<List<DailyUsageResponse>> response = cycleController.getCurrentCycleDailyUsage("invalidUserId", "validMdn");
            assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        } catch (CustomException ex) {
            assertEquals("Invalid userId", ex.getMessage());
        }

    }

    @Test
    public void testGetCurrentCycleDailyUsage_InvalidMdn() throws CustomException {
        doThrow(new CustomException("Invalid mdn")).when(validator).validatePhoneNumber(anyString());

        try {
            ResponseEntity<List<DailyUsageResponse>> response = cycleController.getCurrentCycleDailyUsage("invalidUserId", "validMdn");
            assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        } catch (CustomException ex) {
            assertEquals("Invalid mdn", ex.getMessage());
        }
    }


    @Test
    public void testGetCycleHistory_Valid() throws CustomException {
        when(cycleService.getCycleHistoryByMdn(anyString(), anyString())).thenReturn(cycleHistoryResponseList);
        cycleHistoryResponseList.add(CycleHistoryResponse.builder().cycleId("1")
                .startDate(LocalDate.now()).endDate(LocalDate.now()).build());
        ResponseEntity<List<CycleHistoryResponse>> response = cycleController.getCycleHistory("validUserId", "validMdn");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cycleHistoryResponseList, response.getBody());
    }

    @Test
    public void testGetCycleHistory_InvalidUserId() throws CustomException {
        doThrow(new CustomException("Invalid userId")).when(validator).validateId(anyString());

        try {
            cycleController.getCycleHistory("invalidUserId", "validMdn");
        } catch (CustomException ex) {
            assertEquals("Invalid userId", ex.getMessage());
        }
    }

    @Test
    public void testGetCycleHistory_InvalidMdn() throws CustomException {
        doThrow(new CustomException("Invalid mdn")).when(validator).validatePhoneNumber(anyString());

        try {
            cycleController.getCycleHistory("validUserId", "invalidMdn");
        } catch (CustomException ex) {
            assertEquals("Invalid mdn", ex.getMessage());
        }
    }


    @Test
    public void testCreateCycle_Valid() throws CustomException {
        when(cycleService.createCycle(anyString(), anyString())).thenReturn(cycle);

        ResponseEntity<Cycle> response = cycleController.createCycle("validUserId", "validMdn");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cycle, response.getBody());
    }

    @Test
    public void testCreateCycle_InvalidUserId() throws CustomException {
        doThrow(new CustomException("Invalid userId")).when(validator).validateId(anyString());

        try {
            cycleController.createCycle("invalidUserId", "validMdn");
        } catch (CustomException ex) {
            assertEquals("Invalid userId", ex.getMessage());
        }
    }

    @Test
    public void testCreateCycle_InvalidMdn() throws CustomException {
        doThrow(new CustomException("1234567890")).when(validator).validatePhoneNumber(anyString());

        try {
            cycleController.createCycle("validUserId", "invalidMdn");
        } catch (CustomException ex) {
            assertEquals("1234567890", ex.getMessage());
        }
    }

    @Test
    public void testCreateCycle_ServiceException() throws CustomException {
        doThrow(new CustomException("Service error")).when(cycleService).createCycle(anyString(), anyString());

        try {
            cycleController.createCycle("validUserId", "validMdn");
        } catch (CustomException ex) {
            assertEquals("Service error", ex.getMessage());
        }
    }
}

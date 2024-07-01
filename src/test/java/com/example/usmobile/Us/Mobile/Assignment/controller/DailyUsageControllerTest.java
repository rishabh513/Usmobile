package com.example.usmobile.Us.Mobile.Assignment.controller;
import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.DailyUsage;
import com.example.usmobile.Us.Mobile.Assignment.repository.DailyUsageRepository;
import com.example.usmobile.Us.Mobile.Assignment.service.DailyUsageService;
import com.example.usmobile.Us.Mobile.Assignment.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyUsageControllerTest {

    @Mock
    private Validator validator;

    @Mock
    private DailyUsageRepository dailyUsageRepository;

    @Mock
    private DailyUsageService dailyUsageService;

    @Mock
    private UserService userService;

    @InjectMocks
    private DailyUsageController dailyUsageController;

    private DailyUsage dailyUsage;

    @BeforeEach
    public void setUp() {
        dailyUsage = new DailyUsage(); // Initialize with sample data
    }

    @Test
    public void testCreateDailyUsage_Valid() throws CustomException {
        when(dailyUsageService.createDailyUsage(anyString(), anyString())).thenReturn(dailyUsage);

        DailyUsage response = dailyUsageController.createDailyUsage("1", "1234567890");

        assertEquals(dailyUsage, response);
    }

    @Test
    public void testCreateDailyUsage_InvalidUserId() throws CustomException {
        doThrow(new CustomException("Invalid userId")).when(validator).validateId(anyString());

        try {
            dailyUsageController.createDailyUsage("1s", "12344");
        } catch (CustomException ex) {
            assertEquals("Invalid userId", ex.getMessage());
        }
    }

    @Test
    public void testCreateDailyUsage_UserServiceException() throws CustomException {
        doThrow(new CustomException("User service error")).when(userService).getUser(anyString(), anyString());

        try {
            dailyUsageController.createDailyUsage("1", "1234567890");
        } catch (CustomException ex) {
            assertEquals("User service error", ex.getMessage());
        }
    }

    @Test
    public void testCreateDailyUsage_DailyUsageServiceException() throws CustomException {
        doThrow(new CustomException("Daily usage service error")).when(dailyUsageService).createDailyUsage(anyString(), anyString());

        try {
            dailyUsageController.createDailyUsage("1", "1234567890");
        } catch (CustomException ex) {
            assertEquals("Daily usage service error", ex.getMessage());
        }
    }

    @Test
    public void testUpdateDailyUsage_Valid() throws CustomException {
        when(dailyUsageService.updateUsage(anyString(), anyDouble())).thenReturn(dailyUsage);

        DailyUsage response = dailyUsageController.updateDailyUsage("1", 100.0);

        assertEquals(dailyUsage, response);
    }

    @Test
    public void testUpdateDailyUsage_InvalidId() throws CustomException {
        doThrow(new CustomException("Invalid id")).when(validator).validateId(anyString());

        try {
            dailyUsageController.updateDailyUsage("1s", 100.0);
        } catch (CustomException ex) {
            assertEquals("Invalid id", ex.getMessage());
        }
    }

    @Test
    public void testUpdateDailyUsage_DailyUsageServiceException() throws CustomException {
        doThrow(new CustomException("Daily usage service error")).when(dailyUsageService).updateUsage(anyString(), anyDouble());

        try {
            dailyUsageController.updateDailyUsage("1", 100.0);
        } catch (CustomException ex) {
            assertEquals("Daily usage service error", ex.getMessage());
        }
    }
}


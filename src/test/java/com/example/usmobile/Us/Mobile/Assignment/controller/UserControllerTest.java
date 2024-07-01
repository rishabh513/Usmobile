package com.example.usmobile.Us.Mobile.Assignment.controller;
import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.model.User;
import com.example.usmobile.Us.Mobile.Assignment.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    Validator validator;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreateUser() throws Exception {
        User userRequest = User.builder().firstName("rish").lastName("rai").email("rish@gmail.com").mdn("7676005333").password("Stringss123@").build();
        User userResponse = User.builder().firstName("rish").lastName("rai").email("rish@gmail.com").mdn("7676005333").password("Stringss123@").build();

        when(userService.createUser(any(User.class))).thenReturn(userResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userResponse)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userResponse.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(userResponse.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value(userResponse.getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(userResponse.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mdn").value(userResponse.getMdn()));


        verify(userService, times(1)).createUser(any(User.class));
    }


}

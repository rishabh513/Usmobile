package com.example.usmobile.Us.Mobile.Assignment.controller;


import com.example.usmobile.Us.Mobile.Assignment.Util.Validator;
import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.User;
import com.example.usmobile.Us.Mobile.Assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private Validator validator;

    @Autowired
    private UserService userService;

    /**
     *  Get all the users
     * @return
     */
    @GetMapping("/users/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Create User
     * @param user
     * @return
     * @throws CustomException
     */
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) throws CustomException {


            // validate the user input
            validator.validateUser(user, false);
            user.setId(null);
            User createdUser = userService.createUser(user);
            logger.info("User created successfully: {}", createdUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    }

    /**
     * Update User
     * @param id
     * @param user
     * @return
     * @throws CustomException
     */
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) throws CustomException {

            // Validate user input
            validator.validateUser(user, false);
            validator.validateId(id);
            // Update user in the service layer
            User updatedUser = userService.UpdateUser(user,id);
            // Return successful response with updated user
            return ResponseEntity.ok(updatedUser);
        }


    /**
     * Transfer MDN
     * @param userId
     * @param mdn
     * @throws CustomException
     */
    @PostMapping("/mdn-transfer")
    public  void transferMdn(@RequestParam("userId") String userId,
                             @RequestParam("mdn") String mdn) throws CustomException {

        validator.validateId(userId);
        validator.validatePhoneNumber(mdn);
        userService.transferMdn(userId,mdn);
    }



}

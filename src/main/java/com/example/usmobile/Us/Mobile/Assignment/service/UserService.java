package com.example.usmobile.Us.Mobile.Assignment.service;

import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.User;
import com.example.usmobile.Us.Mobile.Assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private EncryptionService encryptionService;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws CustomException {
        //password encryption is needed
        //Check if phone number exists
        //if YES -> Throw Error
//        String encodedPassword = encryptionService.encodePassword(user.getPassword());
//        user.setPassword(encodedPassword);
        if(userRepository.existsByMdn(user.getMdn())){
            throw new CustomException("Phone Number already exists");
        }
        return userRepository.insert(user);
    }

    public User UpdateUser(User user, String userid) throws CustomException {

        Optional<User> optionalUser = userRepository.findById(userid);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            user.setId(updatedUser.getId());
            user.setCreatedDate(updatedUser.getCreatedDate());
        } else {
            throw new CustomException("User not found for given ID");
        }
        return userRepository.save(user);
    }


    public User transferMdn(String userid, String mdn) throws CustomException {

        // Fetch users
        boolean sourceUser = userRepository.existsByMdn(mdn);
        if (sourceUser) {
            throw new CustomException("Phone number is already associated with another user");
        }
        //first we are fetching the user from database setting the new phone number
        //and we are going to save it back
        Optional<User> sourceUserOptional = userRepository.findById(userid);
        if (sourceUserOptional.isPresent()) {
            User srcUser = sourceUserOptional.get();
            srcUser.setMdn(mdn);
            return userRepository.save(srcUser);
        } else {
            throw new CustomException("No user found for given User ID");
        }


    }

    public User getUser(String userId, String mdn) throws CustomException {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new CustomException("No user present");
        } else {
            User currentUser = user.get();
            if(!currentUser.getMdn().equals(mdn))
                throw new CustomException("Invalid mdn");
            return currentUser;
        }
    }


}

package com.example.usmobile.Us.Mobile.Assignment.Util;

import com.example.usmobile.Us.Mobile.Assignment.exception.CustomException;
import com.example.usmobile.Us.Mobile.Assignment.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@Slf4j
public class Validator {

    private final String regexForName = "^[a-zA-Z]+$";

    private final  String regexForEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private final  String regexForPassword = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private final  String regexForMdn = "^[0-9]{10}$";
    private final  String regexForUserId = "^[0-9a-z]{24}$";

    public void validateUser(User user, boolean ignorepassword) throws CustomException {

        if (user == null) {
            log.error("Received null user input.");
            throw new CustomException("Received null user input.");
        }

        //check if the email is valid
        if(user.getEmail() != null){
            if(!Pattern.compile(regexForEmail).matcher(user.getEmail()).matches()){
                throw new CustomException("Invalid email format.");
            }
        }
        else {
            throw new CustomException("Email is required.");
        }

        //only lower
        if(user.getFirstName() != null){
            if(!Pattern.compile(regexForName).matcher(user.getFirstName()).matches())
                throw new CustomException("Invalid first name format. Only lowercase and uppercase letter are allowed");
        } else {
            throw new CustomException("First name is required.");
        }

        if(user.getLastName() != null){
            if(!Pattern.compile(regexForName).matcher(user.getLastName()).matches())
                throw new CustomException("Invalid Last name format.Only lowercase and uppercase letter are allowed");
        } else {
            throw new CustomException("Last name is required.");
        }

        if(!ignorepassword){
            if(user.getPassword() != null){
                if(!Pattern.compile(regexForPassword).matcher(user.getPassword()).matches())
                    throw new CustomException("password must contain atleast one digit, one lower case , one uppercase , one special character. Must not contain whitespace and must be atleast 8 characters long");
            } else {
                throw new CustomException("Password is required.");
            }
        }

        validatePhoneNumber(user.getMdn());
    }


    public void validatePhoneNumber(String mdn) throws CustomException {
        if(mdn != null){
            if(!Pattern.compile(regexForMdn).matcher(mdn).matches())
                throw new CustomException("Phone Number must be 10 Digit only");
        } else {
            throw new CustomException("Phone Number is required.");
        }
    }

    public void validateId(String userid) throws CustomException {
        //regexForUserId
        if(!Pattern.compile(regexForUserId).matcher(userid).matches())
            throw new CustomException("Invalid User ID Provided");

        }

}

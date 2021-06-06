package com.example.shiv.myapplication.utils;

import com.example.shiv.myapplication.modals.User;

import java.util.HashMap;
import java.util.Map;

public class LoginUtils {

    public static Map<String , String> formValidation(User user){

        Map<String, String> validationResult = new HashMap<>();

        validationResult.put(Constants.STATUS , Constants.VALIDATION_STATUS_FAILED);
        if(user.getUsername().equals("") || user.getCode().equals("")){
            validationResult.put(Constants.ERROR , Constants.LOGIN_ATTRIBUTE.USERNAME_PASSWORD_NOT_EMPTY);
            return validationResult;
        }
        validationResult.put(Constants.STATUS , Constants.VALIDATION_STATUS_SUCCESS);
        return validationResult;
    }
}

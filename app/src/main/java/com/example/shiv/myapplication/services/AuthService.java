package com.example.shiv.myapplication.services;

import android.util.Log;
//import android.support.annotation.Nullable;

import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public AuthService(){ }

    public String getTest(String str){
        return str+"test me";
    }

    public Map<String , String> login(Map< String, Object> authResponse){

        Map<String, String> loginResult = new HashMap<>();
        loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_FAILED);
        loginResult.put(Constants.ERROR , Constants.LOGIN_ATTRIBUTE.USERNAME_PASSWORD_INCORRECT);

        try{
            String userJson = ServiceBinder.getJsonFromObject(authResponse.get(Constants.USER));
            Gson g = new Gson();
            String content = g.toJson(authResponse.get(Constants.USER));
            User user = g.fromJson(content, User.class);
            Log.i("login", "API-UserJson: "+content);
            Log.i("login", "API-User: "+user);
            //ObjectMapper MAPPER = new ObjectMapper();
            String token = authResponse.get(Constants.API_TOKEN).toString();

            if(token != null){
                SessionManager.INSTANCE.setUser(user);
                SessionManager.INSTANCE.setAPIToken(token);
                loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_SUCCESS);
            }
        }catch (Exception ex){
            Log.e("Auth" , "Failed to login"+ex.getLocalizedMessage());
        }
        return loginResult;
    }

}

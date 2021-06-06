package com.example.shiv.myapplication.config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.services.ServiceBinder;
import com.example.shiv.myapplication.utils.Constants;

public class SessionManager {

    public static SessionManager INSTANCE = null;

    private SharedPreferences sharedPreferences;

    public static void build(Context context){
        if(INSTANCE == null){
            INSTANCE = new SessionManager(context);
        }
    }

    private SessionManager(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setUser(User user){
        String userData = ServiceBinder.getJsonFromObject(user);
        sharedPreferences.edit().putString(Constants.USER, userData).commit();;
    }

    public User getUser(){
        String userData = sharedPreferences.getString(Constants.USER , "");
        User user = null;
        if(userData != null) {
            user = (User) ServiceBinder.getObjectFromJson(userData, User.class);
        }
        return user;
    }

    public void setAPIToken(String apiToken){
        sharedPreferences.edit().putString(Constants.API_TOKEN, apiToken).commit();;
    }

    public String getAPIToken(){
        return sharedPreferences.getString(Constants.API_TOKEN , "");
    }

    public void destroy(){
        sharedPreferences.edit().remove(Constants.API_TOKEN).commit();
        sharedPreferences.edit().remove(Constants.USER).commit();
        INSTANCE = null;
    }
}

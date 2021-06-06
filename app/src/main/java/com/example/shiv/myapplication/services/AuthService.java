package com.example.shiv.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
//import android.support.annotation.Nullable;

import com.example.shiv.myapplication.R;
import com.example.shiv.myapplication.config.ApiClient;
import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.repositories.UserRepository;
import com.example.shiv.myapplication.utils.Constants;
import com.example.shiv.myapplication.utils.LoginUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthService {

    public AuthService(){ }

    public String getTest(String str){
        return str+"test me";
    }

    public Map<String , String> login(User user){

        Map<String, String> loginResult = new HashMap<>();
        loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_FAILED);
        loginResult.put(Constants.ERROR , Constants.LOGIN_ATTRIBUTE.USERNAME_PASSWORD_INCORRECT);

        if(user.getUsername().equals("test")){
            SessionManager.INSTANCE.setUser(user);
            loginResult.put(Constants.STATUS , Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_SUCCESS);

            Log.i("login", "API-User: "+user);
        }

        return loginResult;
    }

}

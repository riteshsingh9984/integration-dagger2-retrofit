package com.example.shiv.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.shiv.myapplication.components.ApplicationComponents;
import com.example.shiv.myapplication.components.DaggerApplicationComponents;
import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;


public class BaseActivity extends Activity {

    static ApplicationComponents applicationComponents;
    static AuthService authService;
    static UserService userService;
    static {
        applicationComponents = DaggerApplicationComponents.builder().build();
        authService = applicationComponents.getAuthService();
        userService = applicationComponents.getUserService();
        Log.i("Init-Services", "services initilized.");
    }

    protected void base(View view){
        // TODO in derived class
        authService.getTest("");
    }

    void signOut() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        startActivity(intent);
        finish();
    }

    public static void headerAction(View view){
        Log.i("HeaderAction","header clicked");
    }

}

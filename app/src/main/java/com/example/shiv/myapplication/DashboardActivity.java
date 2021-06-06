package com.example.shiv.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.modals.User;

public class DashboardActivity extends BaseActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        sessionManager = SessionManager.INSTANCE;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(SessionManager.INSTANCE ==null){
            signOut();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    public void showUserProfile(View view){
        // Closing ActivityMain and redirecting on DashboardActivity
        Intent intent = new Intent( DashboardActivity.this , UserActivity.class);
        startActivity(intent);
    }

    @Override
    public void base(View view) {
        userService.userTest();
        Log.i("B" , "Base Activity "+sessionManager.getUser());
    }

}

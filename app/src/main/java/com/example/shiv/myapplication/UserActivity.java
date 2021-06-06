package com.example.shiv.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.myannotations.BehavioOne;

public class UserActivity extends BaseActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        sessionManager = SessionManager.INSTANCE;

        User user = sessionManager.getUser();
        Log.i("U" , "U-session "+user);
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

    public void sessionClean(View view){
        sessionManager.destroy();
    }

    @Override
    @BehavioOne
    public void base(View view) {
        Log.i("B" , "Base Activity");
    }
}

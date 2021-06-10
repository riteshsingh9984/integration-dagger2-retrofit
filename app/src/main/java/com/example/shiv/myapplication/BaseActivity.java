package com.example.shiv.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shiv.myapplication.components.ApplicationComponents;
import com.example.shiv.myapplication.components.DaggerApplicationComponents;
import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;

import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return(true);
            case R.id.about:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return(true);
            case R.id.settings:
                Toast.makeText(this, R.string.about_toast, Toast.LENGTH_LONG).show();
                return(true);
            case R.id.exit:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }

}

package com.example.shiv.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shiv.myapplication.config.ApiClient;
import com.example.shiv.myapplication.config.SessionManager;
import com.example.shiv.myapplication.dtos.Response;
import com.example.shiv.myapplication.modals.User;
import com.example.shiv.myapplication.repositories.AuthRepository;
import com.example.shiv.myapplication.repositories.UserRepository;
import com.example.shiv.myapplication.services.ServiceBinder;
import com.example.shiv.myapplication.utils.Constants;
import com.example.shiv.myapplication.utils.LoginUtils;

import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity implements ServiceBinder {

    private long pressedTime;
    private EditText username;
    private EditText password;
    private UserRepository userRepository;
    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SessionManager.build(getApplicationContext());
        userRepository = ApiClient.getRetrofit(true).create(UserRepository.class);
        authRepository = ApiClient.getRetrofit(false).create(AuthRepository.class);
    }

    public void testMe(View view){
        /*String data = authService.getTest("Raman");
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();*/
        Intent intent = new Intent(MainActivity.this, IndexActivity.class);
        startActivity(intent);
    }

    public void getLogin(View view){

        User user = getLoginCredintials();

        if(user != null && !user.getUsername().equals("")){
            Log.i("API-call", user.toString());
            //Call<User> userByUsernameCall = userRepository.findUserByUsername(user.getUsername());
            Call<Response<Map< String, Object>>> auth = authRepository.auth(user);

            // Async API Call
            auth.enqueue(new Callback<Response<Map< String, Object>>>() {
                @Override
                public void onResponse(Call<Response<Map< String, Object>>> call, retrofit2.Response<Response<Map< String, Object>>> response) {

                    if(response.isSuccessful()) {
                        Response<Map< String, Object>> auth = response.body();
                        if(auth.getStatus() == 200){
                            final Map<String, String> loginResult = authService.login(auth.getData());
                            if(loginResult != null && loginResult.get(Constants.STATUS).equals(Constants.LOGIN_ATTRIBUTE.LOGIN_STATUS_SUCCESS)) {
                                Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                                startActivity(intent);

                            }
                        }else{
                            Toast.makeText(MainActivity.this, "API not allow to user in the system", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "User not Authorized.", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Response<Map< String, Object>>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "API Down", Toast.LENGTH_LONG).show();
                    call.cancel();
                }
            });
        }
    }

    private User getLoginCredintials(){

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        User user = new User( null, username.getText().toString(), password.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        Map<String , String> validationResult = LoginUtils.formValidation(user);
        if(validationResult.get(Constants.STATUS).equals(Constants.VALIDATION_STATUS_FAILED) &&
                validationResult.get(Constants.ERROR).equals(Constants.LOGIN_ATTRIBUTE.USERNAME_PASSWORD_NOT_EMPTY)){

            username.setError("Username can not be empty.");
            password.setError("Password can not be empty.");
            return null;
        }
        return user;
    }

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public void base(View view) {

        Log.i("B" , "Base Activity"+authService.getTest("Raman"));
    }

}

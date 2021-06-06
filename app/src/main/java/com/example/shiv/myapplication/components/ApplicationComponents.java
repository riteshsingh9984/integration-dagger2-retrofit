package com.example.shiv.myapplication.components;

import com.example.shiv.myapplication.modules.ServicesModule;
import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;

import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component( modules = {ServicesModule.class})
public interface ApplicationComponents {

    public AuthService getAuthService();
    public UserService getUserService();

}

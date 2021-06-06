package com.example.shiv.myapplication.modules;

import com.example.shiv.myapplication.services.AuthService;
import com.example.shiv.myapplication.services.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    @Provides
    @Singleton
    public AuthService getAuthService(){
        return new AuthService();
    }

    @Provides
    @Singleton
    public UserService getUserService(){
        return new UserService();
    }
}

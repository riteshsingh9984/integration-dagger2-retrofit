package com.example.shiv.myapplication.repositories;

import com.example.shiv.myapplication.modals.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserRepository {

    @GET("rest/user/{username}")
    Call<User> findUserByUsername(@Path("username") String username);
}

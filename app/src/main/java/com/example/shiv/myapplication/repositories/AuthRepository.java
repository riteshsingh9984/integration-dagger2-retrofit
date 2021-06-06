package com.example.shiv.myapplication.repositories;

import com.example.shiv.myapplication.dtos.Response;
import com.example.shiv.myapplication.modals.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthRepository {

    @POST("auth")
    Call<Response<Map<String, Object>>> auth(@Body User user);
}

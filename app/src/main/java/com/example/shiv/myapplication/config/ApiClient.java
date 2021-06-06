package com.example.shiv.myapplication.config;

import com.example.shiv.myapplication.utils.APIConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static HttpLoggingInterceptor getHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return httpLoggingInterceptor;
    }

    private static Interceptor getInterceptor(boolean isSecured){
        if(isSecured) {
            return new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader(APIConstants.API_CONTENT_KEY, APIConstants.API_CONTENT_VALUE)
                            // .addHeader(APIConstants.API_TOKEN_KEY, SessionManager.INSTANCE.getAPIToken())
                            //.addHeader(APIConstants.API_TOKEN_KEY, "Bearer " + SessionManager.INSTANCE.getAPIToken())
                            .build();

                    return chain.proceed(newRequest);
                }
            };
        }

        return new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .addHeader(APIConstants.API_CONTENT_KEY, APIConstants.API_CONTENT_VALUE)
                        //.addHeader(APIConstants.ADMIN_REQUEST_KEY, APIConstants.ADMIN_REQUEST_VALUE)
                        //.addHeader(APIConstants.API_TOKEN_KEY, "Bearer " + SessionManager.INSTANCE.getAPIToken())
                        .build();

                return chain.proceed(newRequest);
            }
        };
    }

    private static OkHttpClient getOkHttpClient(boolean isSecured){
        return new OkHttpClient.Builder()
                .addInterceptor(getInterceptor(isSecured))
                .addInterceptor(getHttpLoggingInterceptor()).build();
    }

    public static Retrofit getRetrofit(Boolean isSecured){
        return new Retrofit.Builder()
                .baseUrl(APIConstants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient(isSecured))
                .build();

    }

}

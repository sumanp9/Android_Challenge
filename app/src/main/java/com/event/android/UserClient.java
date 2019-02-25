package com.event.android;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserClient {

    @POST("v1/login/")
    Call<User> createAccount(@Body User user);

    @GET("v1/events/")
    Call<ResponseBody> getSecret(@Header("Authorization") String authToken);
}

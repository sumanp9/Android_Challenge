package com.event.android.Service;

import com.event.android.userClass.Event;
import com.event.android.userClass.SpeakerBio;
import com.event.android.userClass.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventInterface {

    //Posts login
    @POST("v1/login/")
    Call<User> createAccount(@Body User user);

    //Gets list of events from the API server
    @GET("v1/events/")
    Call<List<Event>> getEvents(@Header("Authorization") String authToken);

    @GET("v1/events/{id}/")
    Call<Event>getEventDetails(@Header("Authorization") String authToken, @Path("id") String id);

    @GET("v1/speakers/{id}")
    Call<SpeakerBio>getSpeakers(@Header("Authorization") String authToken, @Path("id") String id);
}


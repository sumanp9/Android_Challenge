package com.event.android.Service;

import com.event.android.userClass.Event;
import com.event.android.userClass.SpeakerBio;
import com.event.android.userClass.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private Retrofit retrofit =  new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private EventInterface eventInterface;
    public APIService(){
        eventInterface = retrofit.create(EventInterface.class);
    }

    public void createAccount(Callback<User> callback, User user){
        eventInterface.createAccount(user).enqueue(callback);
    }

    public void getEvents(Callback<List<Event>>call, String token){
        eventInterface.getEvents(token).enqueue(call);
    }

    public void getEventDetail(Callback<Event> callback, String token , String id){
        eventInterface.getEventDetails(token,id).enqueue(callback);
    }
/*
    public void getEventList(Call<List<Event>> call, String token){
        eventInterface.getSecret(token).enqueue((Callback<List<Event>>) call);
    }
*/
    public void getSpeakers(Callback<SpeakerBio> call, String token, String id){
        eventInterface.getSpeakers(token, id).enqueue((Callback<SpeakerBio>) call);

    }


}
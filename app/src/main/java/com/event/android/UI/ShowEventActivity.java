package com.event.android.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.event.android.R;
import com.event.android.Service.APIService;
import com.event.android.userClass.Event;
import com.event.android.userClass.Speaker;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowEventActivity extends AppCompatActivity {

    @BindView(R.id.eventImage)
    ImageView eventImage;
    @BindView(R.id.eventTitle)
    TextView eventTitle;
    @BindView(R.id.eventDateTime)
    TextView eventDateTime;
    @BindView(R.id.eventDetail)
    TextView eventDetail;
    @BindView(R.id.location_details)
    TextView locationDetails;
    @BindView(R.id.speakerRecyclerView)
    RecyclerView speakerRecyclerView;

    public ArrayList<Integer> speakeridList = new ArrayList<>();
    public ArrayList<String> arraySpeakerName = new ArrayList<>();
    public ArrayList<String> arraySpeakerBio = new ArrayList<>();
    public ArrayList<String> arraySpeakerImage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event);
        ButterKnife.bind(this);
        String event_Id = getIntent().getStringExtra("eventID");
        String token = getIntent().getStringExtra("token");

        startServices(token, event_Id);


    }


    private void startServices(final String token, final String event_Id) {
        APIService apiService = new APIService();
        apiService.getEventDetail(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {
                    displayEvent(response, token, event_Id);
                } else {
                    Toast.makeText(ShowEventActivity.this, "Event response unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Toast.makeText(ShowEventActivity.this, "Event Loading Failed", Toast.LENGTH_SHORT).show();

            }
        }, token, event_Id);
    }


    private void displayEvent(Response<Event> response, String token, String event_Id) {
        Event event = response.body();
        //setting event image using picasso
        Picasso.get()
                .load(event.getImageUrl())
                .into(eventImage);
        eventTitle.setText(event.getTitle());
        eventDetail.setText(event.getEventDescription());
        eventDateTime.setText(event.getStartDateTime());
        locationDetails.setText(event.getLocation());

        for (int i = 0; i < event.getSpeakers().size(); i++) {
            Speaker speaker = event.getSpeakers().get(i);

            speakeridList.add(speaker.getId());
        }

        //showSpeakers(token, event_Id, speakeridList);

    }

/*
    private void showSpeakers(final String token, String event_id, ArrayList<Integer> speakeridList) {

        for (int i = 0; i < speakeridList.size(); i++) {
            getEventSpeakers(speakeridList.get(i).toString(), token);
        }
    }

    private void getEventSpeakers(final String speakerId, String token) {
        APIService apiService = new APIService();
        Toast.makeText(this, "Speaker Id is " + token, Toast.LENGTH_SHORT).show();
        apiService.getSpeakers(new Callback<SpeakerBio>() {
            @Override
            public void onResponse(Call<SpeakerBio> call, Response<SpeakerBio> response) {
                Toast.makeText(ShowEventActivity.this, "In Speaker response", Toast.LENGTH_SHORT).show();
                if (response.isSuccessful()) {
                    SpeakerBio bio = (SpeakerBio) response.body();
                    arraySpeakerName.add(bio.getFirstName() + " " + bio.getLastName());
                    arraySpeakerImage.add(bio.getImageUrl());
                    arraySpeakerBio.add(bio.getBio());
                } else {
                    Toast.makeText(ShowEventActivity.this, "Speaker Deatail Retreval unsuccesful", Toast.LENGTH_SHORT).show();
                }

                //RecyclerView to display Speaker bio and information
                intiSpeakerView(arraySpeakerName, arraySpeakerImage, arraySpeakerBio);

            }

            @Override
            public void onFailure(Call<SpeakerBio> call, Throwable t) {
                //Toast.makeText(ShowEventActivity.this, "Retrival Failed : On failure", Toast.LENGTH_SHORT).show();
            }
        }, token, speakerId);


    }

    private void intiSpeakerView(ArrayList<String> arraySpeakerName, ArrayList<String> arraySpeakerImage, ArrayList<String> arraySpeakerBio) {
        RecyclerView recyclerView = speakerRecyclerView;
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        SpeakerViewAdapter adapter = new SpeakerViewAdapter(arraySpeakerName,arraySpeakerBio, arraySpeakerImage, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/
}

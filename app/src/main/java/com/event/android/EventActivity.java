package com.event.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.event.android.StoredData.Prefeneces;
import com.event.android.userClass.Event;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventActivity extends AppCompatActivity {

    private EditText editText2;
    private Toolbar toolbar;

    private String token="";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/api/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient =  retrofit.create(UserClient.class);

    UserClient eventInterface =  retrofit.create(UserClient.class);

    ArrayList<String> id = new ArrayList<>();
    ArrayList<String>imageUrls =  new ArrayList<>();
    ArrayList<String>imageTitle = new ArrayList<>();
    ArrayList<String>imageDate = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        token =  getIntent().getStringExtra("StoredToken");
        toolbar = findViewById(R.id.event_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");

        getEvents(token);
        //textView.setText(Prefeneces.getUsername(EventActivity.this));
    }

    private void getEvents(final String token) {
        Call<List<Event>> call =  eventInterface.getSecret(token);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                String ofEvents = "";
                try{
                    List<Event> events =  response.body();
                    for (int i = 0; i< events.size();i++){
                        Event e =  response.body().get(i);
                        id.add(e.getId().toString());
                        imageTitle.add(e.getTitle());
                        imageUrls.add(e.getImageUrl());
                        imageDate.add(e.getStartDateTime());
                    }
                    Toast.makeText(EventActivity.this, "Events recieved", Toast.LENGTH_SHORT).show();

                    initRecyclerView(id, imageTitle,imageUrls,imageDate);
                    //editText.setText(ofEvents);
                }
                catch (Exception e){
                    Log.d("onResponse", "there is an error");
                    e.printStackTrace();
                }
            }

             @Override
             public void onFailure(Call<List<Event>> call, Throwable t) {
                 Log.d("onFailure",t.toString());

             }
         });



    }

    private void initRecyclerView(ArrayList<String> id, ArrayList<String> imageTitle, ArrayList<String> imageUrls, ArrayList<String> imageDate) {
        RecyclerView recyclerView =  findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,id,imageUrls,imageTitle,imageDate);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem logout_option) {
        switch (logout_option.getItemId()){
            case R.id.option:
                logout_User();
                return true;
            
        }
        return super.onOptionsItemSelected(logout_option);
    }

    private void logout_User() {
        Prefeneces.saveUserData("","","",this);
        Intent intent =  new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

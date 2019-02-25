package com.event.android;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EventActivity extends AppCompatActivity {

    private EditText editText2;
    private Toolbar toolbar;

    private static String token="";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://challenge.myriadapps.com/api/")
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    UserClient userClient =  retrofit.create(UserClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        editText2 =  findViewById(R.id.editText2);

        token =  getIntent().getStringExtra("StoredToken");

        toolbar = findViewById(R.id.event_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Events");

        getEvents(token);
        //textView.setText(Prefeneces.getUsername(EventActivity.this));
    }

    private void getEvents(final String token) {
         Call<ResponseBody> call =  userClient.getSecret(token);
         call.enqueue(new Callback<ResponseBody>() {
             @Override
             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                 if (response.isSuccessful()){
                     try {
                         editText2.setText(response.body().string());
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 }
                 else{
                     editText2.setText(token);
                     Toast.makeText(EventActivity.this, "Not succesful", Toast.LENGTH_SHORT).show();
                 }
             }

             @Override
             public void onFailure(Call<ResponseBody> call, Throwable t) {

                 Toast.makeText(EventActivity.this, "call failed", Toast.LENGTH_SHORT).show();
             }
         });



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

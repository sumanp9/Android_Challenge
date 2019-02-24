package com.event.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private Button btnLogin;
    private static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        checkUser();

        btnLoginPressed();
    }

    private void checkUser() {
        if (Prefeneces.getUsername(this)!=null || !Prefeneces.getUsername(this).equals("")){
            Intent intent =  new Intent(LoginActivity.this, EventActivity.class);
            startActivity(intent);

        }
    }

    //Function that runs after the button is pressed
    private void btnLoginPressed() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String pass =  txtPassword.getText().toString();

                if(!checkfieldEmpty(username, pass)){
                    User user  = new User(username,pass);
                    postUser(user,username,pass);

                }
            }
        });
    }

    private void postUser(User user, final String username, final String pass) {
        Retrofit.Builder builder =  new Retrofit.Builder()
                .baseUrl("https://challenge.myriadapps.com/api/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit =  builder.build();
        UserClient client  = retrofit.create(UserClient.class);



        Call<User> call =  client.createAccount(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Success "+response.body().getToken(), Toast.LENGTH_SHORT).show();
                    token =  response.body().getToken();
                    Prefeneces.saveUserData(username,pass,token,LoginActivity.this);

                    // saveUserInfo();
                    openEvents(username, pass, token);
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Unsucessful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Request Failed. ", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openEvents(String username, String pass, String token) {
        Intent eventIntent =  new Intent(LoginActivity.this,EventActivity.class);
        eventIntent.putExtra("Username",username);
        eventIntent.putExtra("Password",pass);
        eventIntent.putExtra("StoredToken",token);
        startActivity(eventIntent);
        finish();


        
    }

    private boolean checkfieldEmpty(String user, String pass) {
        if (user.equals("")||pass.equals("")){
            return true;
        }
        else
            return false;
    }

    public  void  saveUserInfo(){
        SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPref.edit();
        editor.putString("Username",txtUsername.getText().toString());
        editor.putString("Password", txtPassword.getText().toString());
        editor.putString("Token",token);

        editor.apply();

        Toast.makeText(this, "Info Saved", Toast.LENGTH_SHORT).show();

    }

}

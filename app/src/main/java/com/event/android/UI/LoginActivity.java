package com.event.android.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.event.android.R;
import com.event.android.Service.APIService;
import com.event.android.StoredData.Prefeneces;
import com.event.android.userClass.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtUsername)
    EditText txtUsername;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    private Button btnLogin;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        if (!Prefeneces.ifExists(this)) {
            checkUser();
        } else {
            Toast.makeText(this, "File " + Prefeneces.ifExists(this), Toast.LENGTH_SHORT).show();
        }

        btnLoginPressed();
    }

    private void checkUser() {

        if (/*Prefeneces.getUsername(this)!=null ||*/ !Prefeneces.getUsername(this).equals("")) {
            String username = Prefeneces.getUsername(this);
            String pass = Prefeneces.getPassword(this);
            String token = Prefeneces.getStoreToken(this);
            Intent intent = new Intent(LoginActivity.this, EventActivity.class);
            intent.putExtra("Username", username);
            intent.putExtra("Password", pass);
            intent.putExtra("StoredToken", token);
            startActivity(intent);
            finish();

        }
    }

    //Function that runs after the button is pressed
    public void btnLoginPressed() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String pass = txtPassword.getText().toString();

                if (!checkfieldEmpty(username, pass)) {
                    User user = new User(username, pass);
                    postUser(user, username, pass);
                } else {
                    Snackbar.make(v, "Please fill all the fields", Snackbar.LENGTH_LONG).show();

                }
            }
        });

    }

    // This function uses retrofit to retrieve the token based on username
    private void postUser(User user, final String username, final String pass) {
        APIService apiService = new APIService();
        apiService.createAccount(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Success " + response.body().getToken(),
                            Toast.LENGTH_SHORT).show();

                    token = response.body().getToken();
                    Prefeneces.saveUserData(username, pass, token, LoginActivity.this);
                    openEvents(username, pass, token);
                } else {
                    Toast.makeText(LoginActivity.this, "Unsucessful",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Request Failed. ",
                        Toast.LENGTH_SHORT).show();
            }
        }, user);

    }

    //This function opens another activity if the token grab is successful.
    private void openEvents(String username, String pass, String token) {
        Intent eventIntent = new Intent(LoginActivity.this, EventActivity.class);
        eventIntent.putExtra("Username", username);
        eventIntent.putExtra("Password", pass);
        eventIntent.putExtra("StoredToken", token);
        startActivity(eventIntent);
        finish();


    }

    //This function checks if the Login and Password fields are empty or not
    //This returns a boolean
    private boolean checkfieldEmpty(String user, String pass) {
        if (user.equals("") || pass.equals("")) {
            return true;
        } else
            return false;
    }


}

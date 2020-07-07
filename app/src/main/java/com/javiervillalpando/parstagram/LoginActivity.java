package com.javiervillalpando.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private EditText usernameLogin;
    private EditText passwordLogin;
    private Button loginButton;
    private Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        if(ParseUser.getCurrentUser() != null){
            goToMainActivity();
        }

        usernameLogin = findViewById(R.id.usernameLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick login button");
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                loginUser(username,password);
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick signup button");
                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();
                signUpUser(username,password);
            }
        });
    }
    private void loginUser(String username, String password) {
        Log.i(TAG, "login successful");
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e !=null){
                    Log.e(TAG,"login issue",e);
                    Toast.makeText(LoginActivity.this,"Incorrect username or password",Toast.LENGTH_SHORT).show();
                    return;
                }
                //If login successful, go to main activity
                goToMainActivity();
                Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }


        });
    }
    private void signUpUser(final String username, final String password){
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    loginUser(username,password);
                } else{
                    Toast.makeText(LoginActivity.this,"Username or password already taken",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void goToMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
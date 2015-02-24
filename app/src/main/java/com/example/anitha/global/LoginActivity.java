package com.example.anitha.global;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anitha.faceon.MainActivity;
import com.example.anitha.faceon.R;
import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by Nora on 23-Feb-15.
 */
public class LoginActivity extends Activity{

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkIfUserIsLoggedIn();
        init();
    }

    private void checkIfUserIsLoggedIn(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            //start sinch
            startAwesomeness();
            startMainActivity();
        }
    }

    private void init(){
       Button loginButton = (Button) findViewById(R.id.loginButton);
       Button signUpButton = (Button) findViewById(R.id.signupButton);
      final EditText usernameField = (EditText) findViewById(R.id.loginUsername);
      final EditText  passwordField = (EditText) findViewById(R.id.loginPassword);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    public void done(ParseUser user, com.parse.ParseException e) {
                        if (user != null) {
                            startAwesomeness();
                            startMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "There was an error logging in.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.signUpInBackground(new SignUpCallback() {
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            startAwesomeness();
                            startMainActivity();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    (CharSequence) e.getCause()
                                    , Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }


    private void startAwesomeness(){
        final Intent awesome = new Intent(getApplicationContext(), GameService.class);
        startService(awesome);
    }

    private void startMainActivity(){
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
    }
}

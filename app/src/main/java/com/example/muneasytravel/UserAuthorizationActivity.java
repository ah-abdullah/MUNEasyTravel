package com.example.muneasytravel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class UserAuthorizationActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private ConstraintLayout userAuthorizationLayout;
    private FirebaseAuth mAuth;
    private UserAuthorizationInterface userAuthorization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authorization);

        getSupportActionBar().hide();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        userAuthorizationLayout = findViewById(R.id.userAuthorizationLayout);

        userAuthorizationLayout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        userAuthorization = new UserAuthorizationFirebase();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            loginUser();
        }
    }

    public void loginUserButton(View view) {
        userAuthorization.setEditTexts(emailEditText,passwordEditText);
        userAuthorization.login();
        try {
            Thread.sleep(2700);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (mAuth.getCurrentUser() != null) {
            loginUser();
        } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(UserAuthorizationActivity.this, "Invalid username/password. Please sign up " +
                            "if you are a new user.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void signupUserButton(View view) {
        userAuthorization.setEditTexts(emailEditText,passwordEditText);
        userAuthorization.signup();
        try {
            Thread.sleep(2700);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (mAuth.getCurrentUser() != null) {
            loginUser();
        } else {
            // If sign up fails, display a message to the user.
            Toast.makeText(UserAuthorizationActivity.this, "Signup failed. Please try again",
                    Toast.LENGTH_SHORT).show();
        }
    }

    // move to main activity
    public void loginUser() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void guestLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), GuestMainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // To ensure pressing enter on the on screen keyboard would also perform the intended search button tasks
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            loginUserButton(v); // perform the Search button tasks
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        // To ensure the onscreen keyboard hides itself when tapped on the application window
        if (v.getId() == R.id.userAuthorizationLayout) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

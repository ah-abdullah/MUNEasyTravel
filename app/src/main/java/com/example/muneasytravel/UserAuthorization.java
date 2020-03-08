package com.example.muneasytravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserAuthorization extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authorization);

        getSupportActionBar().hide();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            loginUser();
        }
    }

    public void loginUserButton(View view) {
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            loginUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(UserAuthorization.this, "Invalid username/password. Please sign up " +
                                            "if you are a new user.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signupUserButton(View view) {
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up user (Add to Firebase Database)
                            FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid()).child("Email").setValue(emailEditText.getText().toString());
                            loginUser();
                        } else {
                            Toast.makeText(UserAuthorization.this, "Signup failed. Please try again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // move to main activity
    public void loginUser() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

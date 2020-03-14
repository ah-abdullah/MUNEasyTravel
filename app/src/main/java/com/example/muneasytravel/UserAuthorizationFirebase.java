package com.example.muneasytravel;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class UserAuthorizationFirebase implements UserAuthorizationInterface {

    private EditText emailEditText;
    private EditText passwordEditText;
    private boolean loginResult;
    private boolean signupResult;

    public UserAuthorizationFirebase() {
        this.loginResult = false;
        this.signupResult = false;
    }

    @Override
    public void setEditTexts(EditText emailEditText, EditText passwordEditText) {
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
    }

    @Override
    public boolean login() {
        loginResult = false;
        if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success
                                loginResult = true;
                            } else {
                                // If sign in fails, display a message to the user.
                                loginResult = false;
                            }
                        }
                    });
        }
        return loginResult;
    }

    @Override
    public boolean signup() {
        signupResult = false;
        if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up user (Add to Firebase Database)
                                signupResult = true;
                                FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid()).child("Email").setValue(emailEditText.getText().toString());
                            } else {
                                signupResult = false;
                            }
                        }
                    });
        }
        return signupResult;
    }
}

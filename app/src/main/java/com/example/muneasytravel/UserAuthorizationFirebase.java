package com.example.muneasytravel;

import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/***********************************************************************************************************
 * UserAuthorizationFirebase implements UserAuthorizationInterface, which will be our authentication service
 */
public class UserAuthorizationFirebase implements UserAuthorizationInterface {

    private EditText emailEditText;
    private EditText passwordEditText;

    // Get the email address, password provided by the user
    @Override
    public void setEditTexts(EditText emailEditText, EditText passwordEditText) {
        this.emailEditText = emailEditText;
        this.passwordEditText = passwordEditText;
    }

    // Process login using Firebase
    @Override
    public void login() {
        if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) { }
                    });
        }
    }

    // Process Signup using Firebase
    @Override
    public void signup() {
        if (!emailEditText.getText().toString().equals("") && !passwordEditText.getText().toString().equals("")) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign up user + Add to Firebase Database)
                                FirebaseDatabase.getInstance().getReference().child("Users").child(task.getResult().getUser().getUid()).child("Email").setValue(emailEditText.getText().toString());
                            }
                        }
                    });
        }
    }
}

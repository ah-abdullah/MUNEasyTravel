package com.example.muneasytravel;

import android.widget.EditText;

/**
 * Interface that any User authentication service can implement
 * In our case, we implemented Firebase authentication service for the project
 */
public interface UserAuthorizationInterface {
    void login();
    void signup();
    void setEditTexts(EditText emailEditText, EditText passwordEditText);
}

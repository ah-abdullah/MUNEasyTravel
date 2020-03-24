package com.example.muneasytravel;

import android.widget.EditText;

public interface UserAuthorizationInterface {
    void login();
    void signup();
    void setEditTexts(EditText emailEditText, EditText passwordEditText);
}

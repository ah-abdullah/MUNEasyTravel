package com.example.muneasytravel;

import android.widget.EditText;

public interface UserAuthorizationInterface {
    boolean login();
    boolean signup();
    void setEditTexts(EditText emailEditText, EditText passwordEditText);
}

package com.example.cursv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Preference;
import com.example.cursv.R;
import com.google.android.material.snackbar.Snackbar;

public class email_and_phone extends DatabaseUtils {

    private final String fullNamePref = "fullName";
    private final String loginPref = "login";
    private final String passwordPref = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_phone);

        String login = Preference.getAuthSettings(loginPref, email_and_phone.this);
        String password = Preference.getAuthSettings(passwordPref, email_and_phone.this);
        String fullName = Preference.getAuthSettings(fullNamePref, email_and_phone.this);
        int humanId = Preference.getIntIdHuman("humanId", email_and_phone.this);
        if(humanId != 0){
            startAddPet();
        }

        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPhone = findViewById(R.id.edPhone);

        ImageButton back = findViewById(R.id.imageButton_back3);
        back.setOnClickListener(view -> finish());

        Button next = findViewById(R.id.button_next3);
        next.setOnClickListener(view -> {
            if(edEmail.length() == 0 || edPhone.length() == 0){
                Snackbar.make(findViewById(android.R.id.content), "Необходимо ввести все данные", Snackbar.LENGTH_SHORT).show();
            }
            else{
                String enterEmail = edEmail.getText().toString();
                String enterPhone = edPhone.getText().toString();

                if (isValidEmail(enterEmail)){
                    long idHuman = addHuman(login, password, fullName, enterEmail, enterPhone);
                    Preference.setIntIdHuman("humanId", (int) idHuman, email_and_phone.this);
                    startAddPet();
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Некорректный адрес электронной почты", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidEmail(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void startAddPet(){
        Intent intent = new Intent(email_and_phone.this, add_pet.class);
        startActivity(intent);
    }
}
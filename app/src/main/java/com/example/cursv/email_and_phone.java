package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class email_and_phone extends DatabaseUtils {

    private final String emailPref = "email";
    private final String phonePref = "phone";
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
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Необходимо ввести Email и номер телефона", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                String enterEmail = edEmail.getText().toString();
                String enterPhone = edPhone.getText().toString();

                int idHuman = addHuman(login, password, fullName, enterEmail, enterPhone);
                Preference.setIntIdHuman("humanId", idHuman, email_and_phone.this);
                startAddPet();
            }
        });
    }

    private void startAddPet(){
        Intent intent = new Intent(email_and_phone.this, add_pet.class);
        startActivity(intent);
    }
}
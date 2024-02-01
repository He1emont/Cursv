package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cursv.Preference;
import com.example.cursv.R;

public class hello_there extends AppCompatActivity {

    private final String fullNamePref = "fullName";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_there);

        String name = Preference.getAuthSettings(fullNamePref, hello_there.this);
        if (!name.equals("")){
            startEmailAndPhoneActivity();
        }

        EditText edFullName = findViewById(R.id.edFullName);

        ImageButton back = findViewById(R.id.imageButton_back);
        back.setOnClickListener(view -> finish());

        Button next = findViewById(R.id.button_next);
        next.setOnClickListener(view -> {
            if(edFullName.length() == 0){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Необходимо ввести полное имя", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                Preference.setAuthSettings(fullNamePref, edFullName.getText().toString(), hello_there.this);
                startEmailAndPhoneActivity();
            }
        });

    }

    private void startEmailAndPhoneActivity(){
        Intent intent = new Intent(hello_there.this, email_and_phone.class);
        startActivity(intent);
    }
}
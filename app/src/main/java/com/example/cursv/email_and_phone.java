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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_and_phone);

        String login = getIntent().getStringExtra("humanLogin");
        String password = getIntent().getStringExtra("humanPassword");
        String fullName = getIntent().getStringExtra("humanFullName");

        EditText edEmail = findViewById(R.id.edEmail);
        EditText edPhone = findViewById(R.id.edPhone);

        ImageButton back = findViewById(R.id.imageButton_back3);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button next = findViewById(R.id.button_next3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edEmail.length() == 0 || edPhone.length() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Необходимо ввести Email и номер телефона", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    String email = edEmail.getText().toString();
                    String phone = edPhone.getText().toString();

                    int idHuman = addHuman(login, password, fullName, email, phone);

                    Intent intent = new Intent(email_and_phone.this, add_pet.class);
                    intent.putExtra("humanId", idHuman);
                    startActivity(intent);
                }
            }
        });

    }
}
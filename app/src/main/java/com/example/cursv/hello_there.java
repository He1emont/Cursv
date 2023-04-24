package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class hello_there extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_there);

        String login = getIntent().getStringExtra("humanLogin");
        String password = getIntent().getStringExtra("humanPassword");

        EditText edFullName = findViewById(R.id.edFullName);

        ImageButton back = findViewById(R.id.imageButton_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button next = findViewById(R.id.button_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edFullName.length() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Необходимо ввести полное имя", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Intent intent = new Intent(hello_there.this, email_and_phone.class);
                    intent.putExtra("humanLogin", login);
                    intent.putExtra("humanPassword", password);
                    intent.putExtra("humanFullName", edFullName.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}
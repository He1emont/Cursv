package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class search extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        int humanId = getIntent().getIntExtra("humanId", 0);

        TextView textHumanName = findViewById(R.id.textHumanName);
        textHumanName.setText(getHumanName(humanId));

        ImageButton back = findViewById(R.id.imageButton_back6);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int humanId = getIntent().getIntExtra("humanId", 0);
        int idPet1 = getIntent().getIntExtra("idPet1", 0);

        TextView textHumanName = findViewById(R.id.textHumanName);
        textHumanName.setText(getHumanName(humanId));

        ImageButton imagePet1 = findViewById(R.id.imagePet1);
        imagePet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PetInfoActivity.class);
                intent.putExtra("idPet1", idPet1);
                startActivity(intent);
            }
        });

        Button next = findViewById(R.id.button_health);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Health.class);
                intent.putExtra("humanId", humanId);
                intent.putExtra("idPet1", idPet1);
                startActivity(intent);
            }
        });
    }
}
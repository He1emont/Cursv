package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Health extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        int humanId = getIntent().getIntExtra("humanId", 0);
        int idPet1 = getIntent().getIntExtra("idPet1", 0);

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(getHumanName(humanId));

        ImageButton back = findViewById(R.id.imageButton_back5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageButton imagePet1 = findViewById(R.id.imagePet1);
        imagePet1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Health.this, PetInfoActivity.class);
                intent.putExtra("idPet1", idPet1);
                startActivity(intent);
            }
        });

        Button next = findViewById(R.id.button_search);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Health.this, search.class);
                intent.putExtra("humanId", humanId);
                startActivity(intent);
            }
        });

        Button next2 = findViewById(R.id.button_search2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Health.this, search.class);
                intent.putExtra("humanId", humanId);
                startActivity(intent);
            }
        });
    }
}
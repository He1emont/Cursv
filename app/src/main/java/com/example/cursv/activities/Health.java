package com.example.cursv.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.R;

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
        back.setOnClickListener(view -> finish());

        ImageButton imagePet1 = findViewById(R.id.imagePet1);
        imagePet1.setOnClickListener(view -> {
            Intent intent = new Intent(Health.this, PetInfoActivity.class);
            intent.putExtra("idPet1", idPet1);
            startActivity(intent);
        });

        Button next = findViewById(R.id.button_search);
        next.setOnClickListener(view -> startSearch(humanId, 1));

        Button next2 = findViewById(R.id.button_search2);
        next2.setOnClickListener(view -> startSearch(humanId, 2));
    }
    private void startSearch(int humanId, int typeService){
        Intent intent = new Intent(Health.this, search.class);
        intent.putExtra("humanId", humanId);
        intent.putExtra("typeService", typeService);
        startActivity(intent);
    }
}
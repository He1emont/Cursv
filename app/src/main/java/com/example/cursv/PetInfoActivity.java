package com.example.cursv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class PetInfoActivity extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_info);

        int idPet1 = getIntent().getIntExtra("idPet1", 0);

        ArrayList<String> infoPet = getPetInfo(idPet1);

        TextView tvName = findViewById(R.id.tvName);
        tvName.setText(infoPet.get(0));
        TextView tvType = findViewById(R.id.tvType);
        tvType.setText(infoPet.get(1));
        TextView tvGender = findViewById(R.id.tvGender);
        tvGender.setText(infoPet.get(2));
        TextView tvDateOfBirth = findViewById(R.id.tvDateOfBirth);
        tvDateOfBirth.setText(infoPet.get(3));

        ImageButton back = findViewById(R.id.imageButton_back2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
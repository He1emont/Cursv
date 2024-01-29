package com.example.cursv;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class HomeActivity extends DatabaseUtils {

    private ImageButton btn_logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int humanId = Preference.getIntIdHuman("humanId", HomeActivity.this);
        int idPet1 = Preference.getIntIdHuman("idPet1", HomeActivity.this);

        btn_logOut = findViewById(R.id.btn_logOut);
        TextView textHumanName = findViewById(R.id.textHumanName);
        textHumanName.setText(getHumanName(humanId));

        ImageButton imagePet1 = findViewById(R.id.imagePet1);
        imagePet1.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, PetInfoActivity.class);
            intent.putExtra("idPet1", idPet1);
            startActivity(intent);
        });

        Button next = findViewById(R.id.button_health);
        next.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, Health.class);
            intent.putExtra("humanId", humanId);
            intent.putExtra("idPet1", idPet1);
            startActivity(intent);
        });
        btn_logOut.setOnClickListener(view -> {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(HomeActivity.this);
            final AlertDialog alertDialog = builder.create();
            builder.setMessage("Вы уверены, что хотите выйти?")
                    .setCancelable(false)
                    .setTitle("Данные будут удалены")
                    .setPositiveButton("Да", (dialog, id) -> logOut())
                    .setNegativeButton("Нет", (dialog, which) -> alertDialog.dismiss())
                    .show();
        });
    }

    private void logOut() {
        Preference.setAuthSettings("login", "", HomeActivity.this);
        Preference.setAuthSettings("password", "", HomeActivity.this);
        Preference.setAuthSettings("fullName", "", HomeActivity.this);
        Preference.setIntIdHuman("humanId", 0, HomeActivity.this);
        Preference.setIntIdHuman("idPet1", 0, HomeActivity.this);
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
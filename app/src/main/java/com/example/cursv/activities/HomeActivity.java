package com.example.cursv.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Preference;
import com.example.cursv.R;

public class HomeActivity extends DatabaseUtils {

    private ImageButton btn_logOut, imagePet1;
    private Button btn_services, next, btn_vet;
    TextView textHumanName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        int humanId = Preference.getIntIdHuman("humanId", HomeActivity.this);
        int idPet1 = Preference.getIntIdHuman("idPet1", HomeActivity.this);

        initViews();
        initListeners(idPet1, humanId);

        textHumanName.setText(getHumanName(humanId));

    }

    private void initViews(){
        btn_services = findViewById(R.id.btn_services);
        btn_logOut = findViewById(R.id.btn_logOut);
        textHumanName = findViewById(R.id.textHumanName);
        imagePet1 = findViewById(R.id.imagePet1);
        next = findViewById(R.id.button_health);
        btn_vet = findViewById(R.id.btn_vet);
    }

    private void initListeners(int idPet1, int humanId){
        imagePet1.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, PetInfoActivity.class);
            intent.putExtra("idPet1", idPet1);
            startActivity(intent);
        });

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
        btn_services.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CustomerServicesActivity.class);
            startActivity(intent);
        });
        btn_vet.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, VeterinariansActivity.class);
            startActivity(intent);
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
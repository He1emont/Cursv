package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class add_pet extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        int humanId = Preference.getIntIdHuman("humanId", add_pet.this);
        int petId = Preference.getIntIdHuman("idPet1", add_pet.this);
        if(petId != 0){
            startHomeActivity();
        }

        EditText edName = findViewById(R.id.edName);
        EditText edType = findViewById(R.id.edType);
        EditText edGender = findViewById(R.id.edGender);
        EditText edDateOfBirth = findViewById(R.id.edDateOfBirth);

        ImageButton back = findViewById(R.id.imageButton_back2);
        back.setOnClickListener(view -> finish());

        Button next = findViewById(R.id.button_next2);
        next.setOnClickListener(view -> {
            if(edName.length() == 0 || edType.length() == 0 || edGender.length() == 0 || edDateOfBirth.length() == 0){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Заполнены не все необходимые поля", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                String name = edName.getText().toString();
                String type = edType.getText().toString();
                String gender = edGender.getText().toString();
                String dateOfBirth = edDateOfBirth.getText().toString();

                int idPet1 = addPet(name, type, gender, dateOfBirth, humanId);
                Preference.setIntIdHuman("idPet1", idPet1, add_pet.this);
                startHomeActivity();
            }
        });
    }

    private void startHomeActivity(){
        Intent intent = new Intent(add_pet.this, HomeActivity.class);
        startActivity(intent);
    }
}
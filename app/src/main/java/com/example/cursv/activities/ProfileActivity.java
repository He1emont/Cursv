package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Human;
import com.example.cursv.Preference;
import com.example.cursv.R;
import com.google.android.material.snackbar.Snackbar;

public class ProfileActivity extends DatabaseUtils {

    private ImageButton iBtn_back;
    private EditText ed_name, ed_email, ed_phone;
    private Human human;
    private Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();
        DataSet();
        initListeners();
    }

    private void initViews(){
        int humanId = Preference.getIntIdHuman("humanId", ProfileActivity.this);
        human = getHumanById(humanId);
        iBtn_back = findViewById(R.id.iBtn_back);
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_email);
        ed_phone = findViewById(R.id.ed_phone);
        btn_save = findViewById(R.id.btn_save);
    }

    private void DataSet(){
        if(human!=null){
            ed_name.setText(human.getFullName());
            ed_email.setText(human.getEmail());
            ed_phone.setText(human.getPhone());
        }
    }

    private void initListeners(){
        iBtn_back.setOnClickListener(view -> {
            next();
        });
        btn_save.setOnClickListener(view -> saveDataHuman());
    }

    private void next(){
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void saveDataHuman() {
        Human newHuman = new Human(human.getId(), human.getLogin(),
                human.getPassword(), ed_name.getText().toString(),
                ed_email.getText().toString(), ed_phone.getText().toString());
        boolean isUpdated = updateHuman(newHuman);
        if (isUpdated) {
            Toast.makeText(getApplicationContext(), "Информация успешно обновлена", Toast.LENGTH_SHORT).show();
            next();
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Ошибка при обновлении информации", Snackbar.LENGTH_SHORT).show();
        }
    }
}
package com.example.cursv.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Preference;
import com.example.cursv.R;

import java.util.Calendar;

public class add_pet extends DatabaseUtils {

    private Spinner spin_gender;
    Calendar dateAndTime = Calendar.getInstance();
    private LinearLayout ll_date;
    private EditText edName, edType;
    private TextView edDateOfBirth;
    private ImageButton back;
    private Button next;
    private int humanId, petId;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        humanId = Preference.getIntIdHuman("humanId", add_pet.this);
        petId = Preference.getIntIdHuman("idPet1", add_pet.this);
        if(petId != 0){
            startHomeActivity();
        }
        initViews();
        setInitialDateTime();
        initListeners();
        initializeAdapterForSpinner();
    }

    private void initViews(){
        spin_gender = findViewById(R.id.spin_gender);
        ll_date = findViewById(R.id.ll_date);
        edName = findViewById(R.id.edName);
        edType = findViewById(R.id.edType);
        edDateOfBirth = findViewById(R.id.edDateOfBirth);
        back = findViewById(R.id.imageButton_back2);
        next = findViewById(R.id.button_next2);
    }

    private void initListeners(){
        back.setOnClickListener(view -> finish());
        next.setOnClickListener(view -> {
            if(edName.length() == 0 || edType.length() == 0 || edDateOfBirth.length() == 0){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Заполнены не все необходимые поля", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                String name = edName.getText().toString();
                String type = edType.getText().toString();
                String dateOfBirth = edDateOfBirth.getText().toString();

                long idPet1 = addPet(name, type, gender, dateOfBirth, humanId);
                Preference.setIntIdHuman("idPet1", (int) idPet1, add_pet.this);
                startHomeActivity();
            }
        });
        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        };
        ll_date.setOnClickListener(view -> {
            new DatePickerDialog(add_pet.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        });
        spin_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                edGender.setText(parent.getItemAtPosition(pos).toString());
                gender = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void setInitialDateTime() {
        edDateOfBirth.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
        ));
        Log.d("Date", edDateOfBirth.getText().toString());
    }

    private void startHomeActivity(){
        Intent intent = new Intent(add_pet.this, HomeActivity.class);
        startActivity(intent);
    }

    private void initializeAdapterForSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_gender.setAdapter(adapter);
    }
}
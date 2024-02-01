package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cursv.Models.Service;
import com.example.cursv.R;

import java.util.Calendar;

public class SigningUpForServiceActivity extends AppCompatActivity {

    private ImageButton ibtn_close;
    private TextView tv_ss_service_name, tv_date_time;
    private LinearLayout ll_ss_date, ll_ss_time;
    private Service service;
    private int humanId;
    private Calendar dateAndTime = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_up_for_service);
        Intent intent = getIntent();
        if (intent != null) {
            service = (Service) intent.getSerializableExtra("service");
            humanId = getIntent().getIntExtra("humanId", 0);
        }
        initViews();
        initListeners();
        tv_ss_service_name.setText(service.getNameService());
    }

    private void initViews(){
        ibtn_close = findViewById(R.id.ibtn_close);
        tv_ss_service_name = findViewById(R.id.tv_ss_service_name);
        tv_date_time = findViewById(R.id.tv_date_time);
        ll_ss_date = findViewById(R.id.ll_ss_date);
        ll_ss_time = findViewById(R.id.ll_ss_time);
    }

    private void initListeners(){
        TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        };
        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        };
        ll_ss_date.setOnClickListener(view ->
                new DatePickerDialog(SigningUpForServiceActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show()
        );
        ll_ss_time.setOnClickListener(view ->
                new TimePickerDialog(SigningUpForServiceActivity.this, t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show()
        );
        ibtn_close.setOnClickListener(view -> finish());
    }

    private void setInitialDateTime() {
        tv_date_time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

}
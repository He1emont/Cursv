package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.R;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class SigningUpForServiceActivity extends DatabaseUtils {

    private ImageButton ibtn_close;
    private TextView tv_ss_service_name, tv_date_time;
    private LinearLayout ll_ss_date, ll_ss_time;
    private Service service;
    private Button btn_signing_up;
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
        Log.d("UserName", getHumanName(humanId));
        initViews();
        initListeners();
        tv_ss_service_name.setText(service.getNameService());
        List<SigningUpForService> signings = getAllSigning();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (SigningUpForService signing : signings) {
            Log.d("SigningUpForService","id: " + signing.getId()
                    + " Date: " + signing.getDate().format(formatter)
                    + " service/human: " + signing.getIdService()
                    + "/" + signing.getIdHuman());
        }
    }

    private void initViews(){
        ibtn_close = findViewById(R.id.ibtn_close);
        tv_ss_service_name = findViewById(R.id.tv_ss_service_name);
        tv_date_time = findViewById(R.id.tv_date_time);
        ll_ss_date = findViewById(R.id.ll_ss_date);
        ll_ss_time = findViewById(R.id.ll_ss_time);
        btn_signing_up = findViewById(R.id.btn_signing_up);
    }

    private void initListeners(){
        TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
            Log.d("SigningUpForService",String.valueOf(dateAndTime.getTimeInMillis()));
        };
        DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            Log.d("SigningUpForService",String.valueOf(dateAndTime.getTimeInMillis()));
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
        btn_signing_up.setOnClickListener(view -> orderingService());
    }

    private void orderingService() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateAndTime.getTimeInMillis()),
                ZoneId.systemDefault()
        );
        SigningUpForService signingUpForService = new SigningUpForService(dateAndTime.getTimeInMillis(), localDateTime, service.getIdService(), humanId);
        long id = addSigningUpForService(signingUpForService);
        Log.d("SigningUpForService",String.valueOf(id));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        SigningUpForService signing = getSigningUpForServiceById(id);
        Log.d("SigningUpForService","id: "
                + signing.getId() + " Date: "
                + signing.getDate().format(formatter) + " service/human: "
                + signing.getIdService() + "/"
                + signing.getIdHuman());
    }

    private void setInitialDateTime() {
        tv_date_time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

}
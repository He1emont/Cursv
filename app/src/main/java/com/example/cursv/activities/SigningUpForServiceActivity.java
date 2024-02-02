package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.R;
import com.google.android.material.snackbar.Snackbar;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private int humanId, idType;
    private Calendar dateAndTime = Calendar.getInstance();
    private EditText et_Address;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signing_up_for_service);
        Intent intent = getIntent();
        if (intent != null) {
            service = (Service) intent.getSerializableExtra("service");
            humanId = getIntent().getIntExtra("humanId", 0);
            idType = getIntent().getIntExtra("idType", 0);//1 - в клинике, 2 - дома
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
        et_Address = findViewById(R.id.et_Address);
        if (idType==1){
            et_Address.setVisibility(View.GONE);
        }
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
        btn_signing_up.setOnClickListener(view -> orderingService());
    }

    private void orderingService() {
        if(idType==1){
            address = "ул. Вавилова 65";
        }
        else{
            address = et_Address.getText().toString();
            if(address.isEmpty()){
                Snackbar.make(findViewById(android.R.id.content), "Введите адрес", Snackbar.LENGTH_SHORT).show();
                return;
            }
        }
        if(tv_date_time.getText().toString().isEmpty()){
            Snackbar.make(findViewById(android.R.id.content), "Выберите дату", Snackbar.LENGTH_SHORT).show();
            return;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateAndTime.getTimeInMillis()),
                ZoneId.systemDefault()
        );

        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();

        LocalTime startTime = LocalTime.of(7, 59);  // Начальное время
        LocalTime endTime = LocalTime.of(19, 0);   // Конечное время

        LocalTime checkTime = LocalTime.of(hour, minute);

        if (checkTime.isAfter(startTime) && checkTime.isBefore(endTime)) {
            // Время больше 8:00 утра и меньше 19:00 вечера
        } else {
            Snackbar.make(findViewById(android.R.id.content), "Мы работаем с 8:00 до 19:00", Snackbar.LENGTH_SHORT).show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        List<SigningUpForService> existingServices = getAllSigning();
        long selectedTimeMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        for (SigningUpForService existingService : existingServices) {
            if(service.getIdService()==existingService.getIdService()){
                LocalDateTime existingStart = existingService.getDate();
                LocalDateTime existingEnd = existingStart.plusMinutes(getServiceById(existingService.getIdService()).getDurationMin());

                long existingStartMillis = existingStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                long existingEndMillis = existingEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                Log.d("existingStart", existingStart.format(formatter));
                Log.d("existingEnd", existingEnd.format(formatter));

                if (selectedTimeMillis > existingStartMillis && selectedTimeMillis < existingEndMillis) {
                    // Время пересекается с существующей записью, выводим сообщение и завершаем метод
                    Snackbar.make(findViewById(android.R.id.content), "Выбранное время уже занято." +
                            "\nВыберите время раньше, либо позднее:\n" +
                            existingStart.format(formatter) + "-" + existingEnd.format(formatter), Snackbar.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        SigningUpForService signingUpForService = new SigningUpForService(dateAndTime.getTimeInMillis(), localDateTime, service.getIdService(), humanId, address);
        long id = addSigningUpForService(signingUpForService);
        SigningUpForService signing = getSigningUpForServiceById(id);
        Log.d("SigningUpForService","id: "
                + signing.getId() + " Date: "
                + signing.getDate().format(formatter) + " service/human: "
                + signing.getIdService() + "/"
                + signing.getIdHuman() + " Address: "
                + signing.getAddress()
        );
//        Intent intent = new Intent(SigningUpForServiceActivity.this, CustomerServicesActivity.class);
        Intent intent = new Intent(SigningUpForServiceActivity.this, CustomerServiceInfoActivity.class);
        intent.putExtra("signing", signing);
        startActivity(intent);
    }

    private void setInitialDateTime() {
        tv_date_time.setText(DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

}
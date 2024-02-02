package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.R;

import java.time.format.DateTimeFormatter;

public class CustomerServiceInfoActivity extends DatabaseUtils {

    private ImageButton iBtn_back;
    private TextView tv_id_service, tv_service_name,
            tv_service_duration, tv_service_doc,
            tv_signing_date_time, tv_signing_address,
            tv_service_cost;
    private SigningUpForService signing;
    private Service service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service_info);
        Intent intent = getIntent();
        if (intent != null) {
            signing = (SigningUpForService) intent.getSerializableExtra("signing");
        }
        initViews();
        initListeners();
        initData();
    }

    private void initViews(){
        iBtn_back = findViewById(R.id.iBtn_back);
        tv_id_service = findViewById(R.id.tv_id_service);
        tv_service_name = findViewById(R.id.tv_service_name);
        tv_service_duration = findViewById(R.id.tv_service_duration);
        tv_service_doc = findViewById(R.id.tv_service_doc);
        tv_signing_date_time = findViewById(R.id.tv_signing_date_time);
        tv_signing_address = findViewById(R.id.tv_signing_address);
        tv_service_cost = findViewById(R.id.tv_service_cost);
    }

    private void initData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        if(signing==null){
            return;
        }
        tv_id_service.setText(String.valueOf(signing.getId()));
        tv_signing_date_time.setText(signing.getDate().format(formatter));
        tv_signing_address.setText(signing.getAddress());
        service = getServiceById(signing.getIdService());
        if(service!=null){
            tv_service_name.setText(service.getNameService());
            tv_service_duration.setText(String.valueOf(service.getDurationMin()));
            tv_service_doc.setText(service.getDoctor());
            tv_service_cost.setText(String.valueOf(service.getCostService()));
        }
    }
    private void initListeners(){
        iBtn_back.setOnClickListener(view -> finish());
    }
}
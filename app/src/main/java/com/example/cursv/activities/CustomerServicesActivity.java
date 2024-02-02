package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.Preference;
import com.example.cursv.R;
import com.example.cursv.adapters.ClientServicesAdapter;
import com.example.cursv.adapters.ServiceAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomerServicesActivity extends DatabaseUtils {

    private ImageButton iBtn_back, iBtn_sort_up, iBtn_sort_down;
    private RecyclerView rv_client_services;
    private int humanId;
    private RelativeLayout rl_not_found;
    private List<SigningUpForService> signing = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_services);
        initViews();
        initializeAdapter();
        initListeners();
    }

    private void initViews(){
        humanId = Preference.getIntIdHuman("humanId", CustomerServicesActivity.this);
        signing = getSigningUpForServiceByHumanId(humanId);
        rv_client_services = findViewById(R.id.rv_client_services);
        rv_client_services.setLayoutManager(new LinearLayoutManager(this));
        rl_not_found = findViewById(R.id.rl_not_found);
        if (signing.size()==0){
            rl_not_found.setVisibility(View.VISIBLE);
        }
        iBtn_back = findViewById(R.id.iBtn_back);
        iBtn_sort_up = findViewById(R.id.iBtn_sort_up);
        iBtn_sort_down = findViewById(R.id.iBtn_sort_down);
    }

    private void initListeners(){
        iBtn_back.setOnClickListener(view -> startHome());
        iBtn_sort_up.setOnClickListener(view -> sortUpOrDown(1));
        iBtn_sort_down.setOnClickListener(view -> sortUpOrDown(2));
    }

    private void sortUpOrDown(int sort){
        if(sort==1){
            signing.sort(Comparator.comparing(SigningUpForService::getDate));
        }
        else{
            signing.sort((service1, service2) -> service2.getDate().compareTo(service1.getDate()));
        }
        initializeAdapter();
    }

    private void startHome(){
        Intent intent = new Intent(CustomerServicesActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeAdapter(){
        ClientServicesAdapter adapterMenu = new ClientServicesAdapter(signing, CustomerServicesActivity.this);
        adapterMenu.notifyDataSetChanged();
        rv_client_services.setAdapter(adapterMenu);
    }
}
package com.example.cursv;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursv.adapters.ServiceAdapter;
import com.example.cursv.Models.Service;

import java.util.List;

public class search extends DatabaseUtils {

    private RecyclerView rv_services;
    private List<Service> services;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        int humanId = getIntent().getIntExtra("humanId", 0);
        services = getAllServices();
        for (Service service : services) {
            Log.d("Service", "ID: " + service.getIdService() + ", Name: " + service.getNameService() + ", Cost: " + service.getCostService());
        }
        rv_services = findViewById(R.id.rv_services);
        rv_services.setLayoutManager(new LinearLayoutManager(this));
        if (services!=null){
            initializeAdapter();
        }
        TextView textHumanName = findViewById(R.id.textHumanName);
        textHumanName.setText(getHumanName(humanId));

        ImageButton back = findViewById(R.id.imageButton_back6);
        back.setOnClickListener(view -> finish());
    }
    //Метод для инициализации адаптера списка
    @SuppressLint("NotifyDataSetChanged")
    private void initializeAdapter(){
        ServiceAdapter adapterMenu = new ServiceAdapter(services, search.this);
        adapterMenu.notifyDataSetChanged();
        rv_services.setAdapter(adapterMenu);
    }
}
package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Veterinarians;
import com.example.cursv.R;
import com.example.cursv.adapters.ClientServicesAdapter;
import com.example.cursv.adapters.VeterinarianAdapter;

import java.util.ArrayList;
import java.util.List;

public class VeterinariansActivity extends DatabaseUtils {

    private RecyclerView rv_veterinarians;
    private ImageButton iBtn_back;
    private List<Veterinarians> veterinariansList = new ArrayList<>();;
    private RelativeLayout rl_not_found;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarians);
        veterinariansList = getAllVeterinarians();
        initViews();
        initializeAdapter();
    }

    private void initViews(){
        rv_veterinarians = findViewById(R.id.rv_veterinarians);
        rv_veterinarians.setLayoutManager(new LinearLayoutManager(this));
        rl_not_found = findViewById(R.id.rl_not_found);
        if (veterinariansList.size()==0){
            rl_not_found.setVisibility(View.VISIBLE);
        }
        iBtn_back = findViewById(R.id.iBtn_back);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeAdapter(){
        VeterinarianAdapter veterinarianAdapter = new VeterinarianAdapter(veterinariansList, VeterinariansActivity.this);
        veterinarianAdapter.notifyDataSetChanged();
        rv_veterinarians.setAdapter(veterinarianAdapter);
    }
}
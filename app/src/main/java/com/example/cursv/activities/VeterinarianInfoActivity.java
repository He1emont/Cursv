package com.example.cursv.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cursv.DatabaseUtils;
import com.example.cursv.Models.Service;
import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.Models.Veterinarians;
import com.example.cursv.Preference;
import com.example.cursv.R;
import com.example.cursv.adapters.ServiceAdapter;
import com.example.cursv.adapters.ServiceVetAdapter;

import java.util.ArrayList;
import java.util.List;

public class VeterinarianInfoActivity extends DatabaseUtils {

    private ImageButton iBtn_back;
    private TextView tv_fio, tv_experience, tv_year;
    private RecyclerView rv_veterinarian_services;
    private RelativeLayout rl_not_found;
    private List<Service> services = new ArrayList<>();
    private Veterinarians veterinarian;
    private int humanId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veterinarian_info);
        Intent intent = getIntent();
        if (intent != null) {
            veterinarian = (Veterinarians) intent.getSerializableExtra("IdDoctor");
        }
        initViews();
        initListeners();
        initData();
    }

    private void initViews(){
        iBtn_back = findViewById(R.id.iBtn_back);
        tv_fio = findViewById(R.id.tv_fio);
        tv_year = findViewById(R.id.tv_year);
        tv_experience = findViewById(R.id.tv_experience);
        rv_veterinarian_services = findViewById(R.id.rv_veterinarian_services);
        rv_veterinarian_services.setLayoutManager(new LinearLayoutManager(this));
        rl_not_found = findViewById(R.id.rl_not_found);
    }

    private void initData(){
        tv_fio.setText(veterinarian.getFio());
        tv_experience.setText(String.valueOf(veterinarian.getExperience()));
        tv_year.setText(getYearText(veterinarian.getExperience()));
        humanId = Preference.getIntIdHuman("humanId", VeterinarianInfoActivity.this);
        services = getServicesByDoctorId(veterinarian.getId());
        if (services!=null){
            initializeAdapter();
        }
    }

    private String getYearText(int experience) {
        String yearText;
        if (experience == 1) {
            yearText = "год";
        } else if (experience >= 2 && experience <= 4) {
            yearText = "года";
        } else {
            yearText = "лет";
        }
        return yearText;
    }
    private void initListeners(){
        iBtn_back.setOnClickListener(view -> finish());
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initializeAdapter(){
        ArrayList<String> infoPet = getPetInfo(Preference.getIntIdHuman("idPet1", VeterinarianInfoActivity.this));
        ServiceVetAdapter serviceVetAdapter = new ServiceVetAdapter(services, VeterinarianInfoActivity.this, infoPet.get(0), humanId);
        serviceVetAdapter.notifyDataSetChanged();
        rv_veterinarian_services.setAdapter(serviceVetAdapter);
    }
}
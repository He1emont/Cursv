package com.example.cursv.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.Models.Veterinarians;
import com.example.cursv.R;
import com.example.cursv.activities.VeterinarianInfoActivity;
import com.example.cursv.activities.VeterinariansActivity;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class VeterinarianAdapter extends RecyclerView.Adapter<VeterinarianAdapter.ViewHolder>{

    List<Veterinarians> veterinariansList;
    VeterinariansActivity context;

    public VeterinarianAdapter(List<Veterinarians> veterinariansList, VeterinariansActivity context) {
        this.veterinariansList = veterinariansList;
        this.context = context;
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.veterinarian_item, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position){
        Veterinarians veterinarian = veterinariansList.get(position);
        viewHolder.tv_fio.setText(veterinarian.getFio());
        viewHolder.tv_experience.setText(String.valueOf(veterinarian.getExperience()));
        viewHolder.cl_veterinarian.setOnClickListener(view -> startVetServiceInfo(veterinarian));
        viewHolder.tv_year.setText(getYearText(veterinarian.getExperience()));
    }

    private void startVetServiceInfo(Veterinarians veterinarian) {
        Intent intent = new Intent(context, VeterinarianInfoActivity.class);
        intent.putExtra("IdDoctor", veterinarian);
        context.startActivity(intent);
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

    @Override
    public int getItemCount() {
        return veterinariansList == null ? 0 : veterinariansList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_fio, tv_experience, tv_year;

        ConstraintLayout cl_veterinarian;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_fio = itemView.findViewById(R.id.tv_fio);
            tv_year = itemView.findViewById(R.id.tv_year);
            tv_experience = itemView.findViewById(R.id.tv_experience);
            cl_veterinarian = itemView.findViewById(R.id.cl_veterinarian);
        }
    }
}

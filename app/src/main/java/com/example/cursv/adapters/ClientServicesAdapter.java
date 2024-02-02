package com.example.cursv.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursv.Models.SigningUpForService;
import com.example.cursv.R;
import com.example.cursv.activities.CustomerServiceInfoActivity;
import com.example.cursv.activities.CustomerServicesActivity;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientServicesAdapter extends RecyclerView.Adapter<ClientServicesAdapter.ViewHolder>{
    List<SigningUpForService> signingUpForServiceList;
    CustomerServicesActivity context;

    public ClientServicesAdapter(List<SigningUpForService> signingUpForServiceList, CustomerServicesActivity context) {
        this.signingUpForServiceList = signingUpForServiceList;
        this.context = context;
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.client_services_item, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position){
        SigningUpForService signingUpForService = signingUpForServiceList.get(position);
        viewHolder.tv_id_signing.setText(String.valueOf(signingUpForService.getId()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        viewHolder.tv_datetime_signing.setText(signingUpForService.getDate().format(formatter));
        viewHolder.cl_client_service.setOnClickListener(view -> startClientServiceInfo(signingUpForService));
    }
    @Override
    public int getItemCount() {
        return signingUpForServiceList == null ? 0 : signingUpForServiceList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_id_signing, tv_datetime_signing;

        ConstraintLayout cl_client_service;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_id_signing = itemView.findViewById(R.id.tv_id_signing);
            tv_datetime_signing = itemView.findViewById(R.id.tv_datetime_signing);
            cl_client_service = itemView.findViewById(R.id.cl_client_service);
        }
    }
    private void startClientServiceInfo(SigningUpForService signing){
        Intent intent = new Intent(context, CustomerServiceInfoActivity.class);
        intent.putExtra("signing", signing);
        context.startActivity(intent);
    }
}

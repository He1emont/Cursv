package com.example.cursv.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursv.R;
import com.example.cursv.Models.Service;
import com.example.cursv.search;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder>{

    List<Service> serviceList;
    search context;
    public ServiceAdapter(List<Service> serviceList, search context){
        this.serviceList = serviceList;
        this.context = context;
    }
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.service_item, viewGroup, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position){
        Service service = serviceList.get(position);
        viewHolder.tv_service_cost.setText(String.valueOf(service.getCostService()));
        viewHolder.tv_service_name.setText(service.getNameService());
    }

    @Override
    public int getItemCount() {
        return serviceList == null ? 0 : serviceList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_service_name, tv_service_cost;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tv_service_name = itemView.findViewById(R.id.tv_service_name);
            tv_service_cost = itemView.findViewById(R.id.tv_service_cost);
        }
    }
}

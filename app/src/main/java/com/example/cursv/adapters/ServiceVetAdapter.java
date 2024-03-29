package com.example.cursv.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursv.Models.Service;
import com.example.cursv.R;
import com.example.cursv.activities.SigningUpForServiceActivity;
import com.example.cursv.activities.VeterinarianInfoActivity;
import com.example.cursv.activities.search;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class ServiceVetAdapter extends RecyclerView.Adapter<ServiceVetAdapter.ViewHolder> {

    List<Service> serviceList;
    VeterinarianInfoActivity context;
    String petName;
    int humanId;
    public ServiceVetAdapter(List<Service> serviceList, VeterinarianInfoActivity context, String petName, int humanId){
        this.serviceList = serviceList;
        this.context = context;
        this.petName = petName;
        this.humanId = humanId;
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
        if(service.getIdType()==1){
            viewHolder.imageView5.setImageResource(R.drawable.ic_clinic);
        }
        viewHolder.cl_service.setOnClickListener(view -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                    context, R.style.BottomSheetDialogTheme
            );
            View bottomSheetView = LayoutInflater.from(context)
                    .inflate(
                            R.layout.bottom_sheet_service_info,
                            context.findViewById(R.id.bottomSheetService)
                    );
            bottomSheetView.findViewById(R.id.v_dismiss).setOnClickListener(view1 -> bottomSheetDialog.dismiss());
            LinearLayout ll_signing = bottomSheetView.findViewById(R.id.ll_signing);
            ll_signing.setOnClickListener(view1 -> serviceSigning(service));
            TextView tv_bs_service_name = bottomSheetView.findViewById(R.id.tv_bs_service_name);
            TextView tv_bs_service_doctor = bottomSheetView.findViewById(R.id.tv_bs_service_doctor);
            TextView tv_bs_service_pet = bottomSheetView.findViewById(R.id.tv_bs_service_pet);
            TextView tv_bs_service_cost = bottomSheetView.findViewById(R.id.tv_bs_service_cost);
            TextView tv_bs_service_duration = bottomSheetView.findViewById(R.id.tv_bs_service_duration);
            tv_bs_service_name.setText(service.getNameService());
            tv_bs_service_doctor.setText(context.getVeterinarianById(service.getIdDoctor()).getFio());
            tv_bs_service_duration.setText(String.valueOf(service.getDurationMin()));

            tv_bs_service_pet.setText(petName);
            tv_bs_service_cost.setText(String.valueOf(service.getCostService()));
            bottomSheetDialog.setContentView(bottomSheetView);
            // Получение Behavior для BottomSheet
            View parent = (View) bottomSheetView.getParent();
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) parent.getLayoutParams();
            CoordinatorLayout.Behavior<View> behavior = params.getBehavior();
            // Проверка, что Behavior является BottomSheetBehavior
            if (behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior<?>) behavior).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            bottomSheetDialog.show();
        });
    }

    private void serviceSigning(Service service) {
        Intent intent = new Intent(context, SigningUpForServiceActivity.class);
        intent.putExtra("idType", service.getIdType());
        intent.putExtra("humanId", humanId);
        intent.putExtra("service", service);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return serviceList == null ? 0 : serviceList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_service_name, tv_service_cost;
        ImageView imageView5;
        ConstraintLayout cl_service;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView5 = itemView.findViewById(R.id.imageView5);
            tv_service_name = itemView.findViewById(R.id.tv_service_name);
            tv_service_cost = itemView.findViewById(R.id.tv_service_cost);
            cl_service = itemView.findViewById(R.id.cl_service);
        }
    }
}

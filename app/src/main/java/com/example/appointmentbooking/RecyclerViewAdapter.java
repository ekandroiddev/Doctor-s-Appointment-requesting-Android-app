package com.example.appointmentbooking;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<DoctorModel> doctorModels;

    public RecyclerViewAdapter(List<DoctorModel> doctorModels) {
        this.doctorModels = doctorModels;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        DoctorModel doctorModel=doctorModels.get(position);
        holder.doctorName.setText(doctorModel.getDoctorName());
        holder.doctorOccupation.setText(doctorModel.getDoctor_occopation());
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "View Holder is clicked", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(v.getContext(), AppointmentBookingActivity.class);
            intent.putExtra("name",doctorModel.getDoctorName());
            v.getContext().startActivity(intent);
        });
        holder.aboutDoctorBtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Not implemented yet", Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public int getItemCount() {
        return doctorModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView doctorProfile;
        TextView doctorName,doctorOccupation;
        ImageButton aboutDoctorBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorProfile=itemView.findViewById(R.id.profile_img);
            doctorName=itemView.findViewById(R.id.doctor_name);
            doctorOccupation=itemView.findViewById(R.id.doctor_occupation);
            aboutDoctorBtn=itemView.findViewById(R.id.about_Btn);
        }
    }
}

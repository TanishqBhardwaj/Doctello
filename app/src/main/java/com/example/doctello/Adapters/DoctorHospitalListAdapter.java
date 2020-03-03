package com.example.doctello.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doctello.R;
import com.example.doctello.models.HospitalDetailsData;
import java.util.List;

public class DoctorHospitalListAdapter extends RecyclerView.Adapter<DoctorHospitalListAdapter.DoctorsViewHolder> {

    private List<HospitalDetailsData> doctorsDataList;

    public DoctorHospitalListAdapter(List<HospitalDetailsData> doctorsDataList) {
        this.doctorsDataList = doctorsDataList;
    }

    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_list_layout,
                parent, false);
        return new DoctorHospitalListAdapter.DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position) {
        HospitalDetailsData doctorsData = doctorsDataList.get(position);
        holder.doctor_name_text_view.setText(doctorsData.getDoctorName());
        holder.doctor_fee_text_view.setText(String.valueOf(doctorsData.getDoctorFees()));
    }

    @Override
    public int getItemCount() {
        if(doctorsDataList!=null) {
            return doctorsDataList.size();
        }
        return 0;
    }

    public class DoctorsViewHolder extends RecyclerView.ViewHolder {
        TextView doctor_name_text_view;
        TextView doctor_fee_text_view;
        TextView doctor_type_text_view;

        public DoctorsViewHolder(@NonNull View itemView) {
            super(itemView);
            doctor_name_text_view = itemView.findViewById(R.id.doctor_name_text_view);
            doctor_fee_text_view = itemView.findViewById(R.id.doctor_fees_text_view);
            doctor_type_text_view = itemView.findViewById(R.id.doctor_type_text_view);
        }
    }
}

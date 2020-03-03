package com.example.doctello.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doctello.R;
import com.example.doctello.models.HospitalServicesData;
import java.util.List;

public class ServicesDataAdapter extends RecyclerView.Adapter<ServicesDataAdapter.ServiceViewHolder> {

    private List<HospitalServicesData> serviceList;

    public ServicesDataAdapter(List<HospitalServicesData> serviceList) {
        this.serviceList = serviceList;
    }

    @NonNull
    @Override
    public ServicesDataAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_layout,
                parent, false);
        return new ServicesDataAdapter.ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesDataAdapter.ServiceViewHolder holder, int position) {
        HospitalServicesData currentModel = serviceList.get(position);
        holder.service_name.setText(currentModel.getModelData().getService_name());
    }

    @Override
    public int getItemCount() {
        if(serviceList!=null) {
            return serviceList.size();
        }
        return 0;
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder{
        TextView service_name;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            service_name = itemView.findViewById(R.id.service_name_text_view);

        }
    }
}

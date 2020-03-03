package com.example.doctello.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doctello.R;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.HospitalData;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeHospitalAdapter extends RecyclerView.Adapter<HomeHospitalAdapter.HomeHospitalViewHolder> {

    private List<HospitalData> hospitalDataList;
    private Context context;
    private HomeHospitalAdapter.OnHospitalClickListener mListener;
    private retrofitInstance instance = new retrofitInstance();

    public HomeHospitalAdapter(List<HospitalData> hospitalDataList, Context context) {
        this.hospitalDataList = hospitalDataList;
        this.context = context;
    }

    public interface OnHospitalClickListener {
        void onCallClick(int position);
        void onHospitalClick(int position);
    }

    public void setOnHospitalClickListener(HomeHospitalAdapter.OnHospitalClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public HomeHospitalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_hospital,
                parent, false);
        return new HomeHospitalAdapter.HomeHospitalViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHospitalViewHolder holder, int position) {
        HospitalData hospitalData = hospitalDataList.get(position);
        holder.textViewHospitalName.setText(hospitalData.getHospitaName());
        holder.textViewHospitalLocation.setText(hospitalData.getHospitalLocation());
        holder.textViewRating.setText(hospitalData.getAverageRating());
        String imageUrl = hospitalData.getHospitalImage();
        Picasso.with(context).load(instance.getURL().concat(imageUrl)).error(R.id.imageViewHospitalProfile)
                .into(holder.imageViewHospitalProfile);
    }

    @Override
    public int getItemCount() {
        if(hospitalDataList!=null) {
            return hospitalDataList.size();
        }
        return 0;
    }

    public class HomeHospitalViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewHospitalName;
        private TextView textViewHospitalLocation;
        private TextView textViewNumberOfDoctors;
        private TextView textViewRating;
        private ImageView imageViewHospitalProfile;
        private ImageView imageViewCall;

        public HomeHospitalViewHolder(@NonNull View itemView,
                                      final HomeHospitalAdapter.OnHospitalClickListener listener) {
            super(itemView);
            textViewHospitalName = itemView.findViewById(R.id.textViewHospitalName);
            textViewHospitalLocation = itemView.findViewById(R.id.textViewLocation);
            textViewNumberOfDoctors = itemView.findViewById(R.id.textViewNumberOfDoctors);
            textViewRating = itemView.findViewById(R.id.textViewRating);
            imageViewHospitalProfile = itemView.findViewById(R.id.imageViewHospitalProfile);
            imageViewCall = itemView.findViewById(R.id.imageViewCall);

            imageViewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCallClick(position);
                        }
                    }
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onHospitalClick(position);
                        }
                    }
                }
            });
        }
    }
}

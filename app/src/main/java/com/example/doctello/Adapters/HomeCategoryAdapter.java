package com.example.doctello.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctello.Local.Entity.CategoryEntity;
import com.example.doctello.R;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.CategoryData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeViewHolder> {

    private List<CategoryEntity> categoryDataList;
    private OnCategoryClickListener mListener;
    private Context context;
    private retrofitInstance instance = new retrofitInstance();

    public HomeCategoryAdapter(List<CategoryEntity> categoryDataList, Context context) {
        this.categoryDataList = categoryDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(int position);
    }

    public void setOnCategoryClickListener(OnCategoryClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category,
                parent, false);
        return new HomeViewHolder(view , mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        CategoryEntity categoryData = categoryDataList.get(position);
        holder.textViewServiceName.setText(categoryData.getServiceName());
        String imageUrl = categoryData.getImage();
        Picasso.with(context).load(instance.getURL().concat(imageUrl)).error(R.id.imageViewCategoryProfile)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(categoryDataList!=null) {
            return categoryDataList.size();
        }
        return 0;
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewServiceName;
        private ImageView imageView;

        public HomeViewHolder(@NonNull View itemView , final OnCategoryClickListener listener) {
            super(itemView);
            textViewServiceName = itemView.findViewById(R.id.textViewServiceName);
            imageView = itemView.findViewById(R.id.imageViewCategoryProfile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onCategoryClick(position);
                        }
                    }
                }
            });
        }
    }
}

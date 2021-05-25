package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.SalonListingActivity;
import com.boog24.modals.homescreen.CategoryDatum;
import com.bumptech.glide.Glide;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder> {

    Context context;
    List<CategoryDatum> categoryData;



    public HomeCategoriesAdapter(Context context, List<CategoryDatum> categoryData) {
        this.context=context;
        this.categoryData=categoryData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_categories_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(categoryData.get(position).getCategoryName());
        Glide.with(context).load(categoryData.get(position).getCategoryImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SalonListingActivity.class);
                intent.putExtra("id",categoryData.get(position).getCategoryId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);
            image=itemView.findViewById(R.id.image);
        }
    }
}

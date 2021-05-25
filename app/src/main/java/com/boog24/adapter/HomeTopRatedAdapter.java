package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.SalonDetailActivity;
import com.boog24.modals.homescreen.SaloonDatum;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeTopRatedAdapter extends RecyclerView.Adapter<HomeTopRatedAdapter.ViewHolder> {

    Context context;
    List<SaloonDatum> saloonData;


    public HomeTopRatedAdapter(Context context, List<SaloonDatum> saloonData) {
        this.context=context;
        this.saloonData=saloonData;
    }

    @NonNull
    @Override
    public HomeTopRatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.home_toprated_row, parent, false);
        return new HomeTopRatedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTopRatedAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(saloonData.get(position).getSaloonImage()).into(holder.imgSalon);
        holder.tvName.setText(saloonData.get(position).getSaloonName());
        holder.tvTotalRating.setText("("+saloonData.get(position).getSaloonRatingsUsers()+")");
        holder.ratingBar.setRating((int) Float.parseFloat(saloonData.get(position).getSaloonRatings()));
        holder.tvAddress.setText(saloonData.get(position).getSaloonAddress());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SalonDetailActivity.class);
                intent.putExtra("salonId",saloonData.get(position).getUserId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return saloonData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAddress,tvTotalRating,tvName;
        RatingBar ratingBar;
        RoundedImageView imgSalon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            tvTotalRating=itemView.findViewById(R.id.tvTotalRating);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tvName=itemView.findViewById(R.id.tvName);
            imgSalon=itemView.findViewById(R.id.imgSalon);
        }
    }
}


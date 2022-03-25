package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.SalonDetailActivity;
import com.boog24.activity.WishlistActivity;
import com.boog24.modals.getWishlist.Datum;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;
    List<Datum> data;
    boolean slection = true;
    WishlistActivity wishlistActivity;

    public WishlistAdapter(Context context, WishlistActivity wishlistActivity, List<Datum> data) {
        this.context=context;
        this.data=data;
        this.wishlistActivity=wishlistActivity;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_listing_row, parent, false);
        return new WishlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {


        Glide.with(context).load(data.get(position).getSalonImage()).into(holder.ivSubCatItem);
        holder.tvTitle.setText(data.get(position).getSalonName());
        holder.tvAddress.setText(data.get(position).getSalonAddress());
        holder.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
        holder.tvRating.setText(data.get(position).getSaloonRatings());
        holder.tvTotalRating.setText("("+data.get(position).getTotalReviews()+")");
        holder.ratingBar.setRating((int) Float.parseFloat(data.get(position).getSaloonRatings()));


        holder.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wishlistActivity.remove(data.get(position).getSalonId());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SalonDetailActivity.class);
                intent.putExtra("salonId",data.get(position).getSalonId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView ivSubCatItem;
        TextView tvTitle,tvAddress,tvRating,tvTotalRating;
        RatingBar ratingBar;
        ImageView ivHeart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSubCatItem=itemView.findViewById(R.id.ivSubCatItem);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tvRating=itemView.findViewById(R.id.tvRating);
            tvTotalRating=itemView.findViewById(R.id.tvTotal);
            ivHeart=itemView.findViewById(R.id.ivHeart);
        }
    }
}


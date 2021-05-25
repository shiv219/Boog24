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
import com.boog24.activity.SalonListingActivity;
import com.boog24.custom.Constants;
import com.boog24.modals.getSaloons.SaloonDatum;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SalonListingAdapter extends RecyclerView.Adapter<SalonListingAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;

    boolean slection = true;
    List<SaloonDatum> saloonData;
    SalonListingActivity salonListingActivity;

    public SalonListingAdapter(Context context, SalonListingActivity salonListingActivity, List<SaloonDatum> saloonData) {
        this.context=context;
        this.saloonData=saloonData;
        this.salonListingActivity=salonListingActivity;
    }

    @NonNull
    @Override
    public SalonListingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_listing_row, parent, false);
        return new SalonListingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonListingAdapter.ViewHolder holder, int position) {


        if (saloonData.get(position).getWishlistStatus().equalsIgnoreCase("1")){
            holder.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
        }else{
            holder.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
        }


        holder.ivHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Prefs.getString(Constants.SharedPreferences_loginKey,"").equalsIgnoreCase(""))
                {
                    salonListingActivity.windowPopUpForLogin(salonListingActivity,context.getResources().getString(R.string.pls_login_first));

                }else {
                    if (holder.ivHeart.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.heart_active).getConstantState())
                        holder.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                    else
                        holder.ivHeart.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));

                    salonListingActivity.addToWishlist(saloonData.get(position).getUserId());
                }
            }
        });


        Glide.with(context).load(saloonData.get(position).getSaloonImage()).into(holder.ivSubCatItem);
        holder.tvTitle.setText(saloonData.get(position).getSaloonName());
        holder.tvAddress.setText(saloonData.get(position).getSaloonAddress());
        holder.tvRating.setText(saloonData.get(position).getSaloonRatings());
        holder.tvTotalRating.setText("("+saloonData.get(position).getSaloonRatingsUsers()+")");
        holder.ratingBar.setRating(Float.parseFloat(saloonData.get(position).getSaloonRatings()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SalonDetailActivity.class);
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


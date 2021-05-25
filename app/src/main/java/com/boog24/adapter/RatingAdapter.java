package com.boog24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.modals.getSaloonDetail.Review;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.ViewHolder> {

    Context context;
    List<Review> reviews;


    public RatingAdapter(Context context, List<Review> reviews) {
        this.context=context;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public RatingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_rating_row, parent, false);
        return new RatingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.ViewHolder holder, int position) {

        holder.ratingBar.setRating(Float.parseFloat(reviews.get(position).getStar()));
        holder.tvComment.setText(reviews.get(position).getDescription());
        holder.tvName.setText(reviews.get(position).getUserName());
        holder.tvtime.setText(reviews.get(position).getCreatedOn().substring(0,11));

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RatingBar ratingBar;
        TextView tvComment,tvName,tvtime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tvComment=itemView.findViewById(R.id.tvComment);
            tvName=itemView.findViewById(R.id.tvName);
            tvtime=itemView.findViewById(R.id.tvtime);
        }
    }
}



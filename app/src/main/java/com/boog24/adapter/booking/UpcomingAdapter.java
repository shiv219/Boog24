package com.boog24.adapter.booking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boog24.R;
import com.boog24.activity.BookingDetailActivity;
import com.boog24.extra.Utils;
import com.boog24.modals.myBookings.DataList;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;

    boolean slection = true;
    List<DataList> dataList;
    String what;
    public UpcomingAdapter(Context context) {
        this.context=context;
    }

    public UpcomingAdapter(Context context, List<DataList> dataList,String what) {
        this.context=context;
        this.dataList=dataList;
        this.what=what;
    }

    @NonNull
    @Override
    public UpcomingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.upcoming_booking_row, parent, false);
        return new UpcomingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAdapter.ViewHolder holder, int position) {


        Glide.with(context).load(dataList.get(position).getSalonImage()).into(holder.ivSubCatItem);
        holder.tvTitle.setText(dataList.get(position).getSalonName());
        holder.tvAddress.setText(Utils.convertDate(dataList.get(position).getAppointmentDate()));
        holder.tvRating.setText("Worker Name :" + dataList.get(position).getWorkerName());
//        holder.tvTotalRating.setText("("+saloonData.get(position).getSaloonRatingsUsers()+")");
//        holder.ratingBar.setRating((int) Float.parseFloat(saloonData.get(position).getSaloonRatings()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookingDetailActivity.class);
                intent.putExtra("type",what);
                intent.putExtra("orderId",dataList.get(position).getOrderId());
                intent.putExtra("salonId",dataList.get(position).getSalonId());
                intent.putExtra("from", "");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView ivSubCatItem;
        TextView tvTitle,tvAddress,tvRating,tvTotalRating;
        RatingBar ratingBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSubCatItem=itemView.findViewById(R.id.ivSubCatItem);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvAddress=itemView.findViewById(R.id.tvAddress);
            ratingBar=itemView.findViewById(R.id.ratingBar);
            tvRating=itemView.findViewById(R.id.tvRating);
            tvTotalRating=itemView.findViewById(R.id.tvTotal);
        }
    }
}


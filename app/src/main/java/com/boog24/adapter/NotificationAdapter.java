package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boog24.R;
import com.boog24.activity.BookingDetailActivity;
import com.boog24.custom.Constants;
import com.boog24.modals.getNotification.NotificationList;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;
    List<NotificationList> notificationList;
    boolean slection = true;


    public NotificationAdapter(Context context, List<NotificationList> notificationList) {
        this.context=context;
        this.notificationList=notificationList;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notifications_row, parent, false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {


        holder.tvTime.setText(notificationList.get(position).getNotificationTime());
        holder.tvTitle.setText(notificationList.get(position).getNotificationMessage());
        Glide.with(context).load(notificationList.get(position).getNotificationImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.putString(Constants.SharedPreferences_notification_id, notificationList.get(position).getUserNotificationId());
                Prefs.putString(Constants.SharedPreferences_order_id, notificationList.get(position).getOrderId());
                Prefs.putString(Constants.SharedPreferences_notification_status, "UPDATE");
                Intent intent = new Intent(context, BookingDetailActivity.class);
                intent.putExtra("type",notificationList.get(position).getType());
                intent.putExtra("orderId",notificationList.get(position).getOrderId());
                intent.putExtra("salonId",notificationList.get(position).getSalonId());
                intent.putExtra("from", notificationList.get(position).getAction());
                intent.putExtra("pre", "Notification");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime,tvTitle;
        CircleImageView image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            image = itemView.findViewById(R.id.image);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}


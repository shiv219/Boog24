package com.boog24.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.activity.ContactDetailActivity;
import com.boog24.modals.getSaloonDetail.SalonService;
import com.boog24.modals.getServiceTimeSlots.Data;
import com.boog24.modals.getServiceTimeSlots.TimeSlot;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    Context context;
    List<TimeSlot> data;
    TimeSlotAdapter.ItemsClick itemsClick;
    int selected=-1;

    public TimeSlotAdapter(Context context, List<TimeSlot> data,TimeSlotAdapter.ItemsClick itemsClick) {
        this.context=context;
        this.data=data;
        this.itemsClick = itemsClick;
    }




    @NonNull
    @Override
    public TimeSlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.time_slot_row, parent, false);
        return new TimeSlotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotAdapter.ViewHolder holder, int position) {

        if (selected==position){
            holder.lyt.setBackground(context.getResources().getDrawable(R.drawable.rounded_border_green));
        }else{
            holder.lyt.setBackground(context.getResources().getDrawable(R.drawable.rounded_bg_gray9c));
        }


        if (data.get(position).getIsAvailable().equalsIgnoreCase("0")){
            holder.lyt.setAlpha((float) 0.4);
        }else{

        }



        holder.tvTime.setText(data.get(position).getFromTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(position).getIsAvailable().equalsIgnoreCase("1")) {
                    selected = position;
                    itemsClick.click(position);
                    notifyDataSetChanged();
                }
            }
        });

        }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTime;
        LinearLayout lyt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime=itemView.findViewById(R.id.tvTime);
            lyt=itemView.findViewById(R.id.lyt);
        }
    }

    public interface ItemsClick {
        void click(int postion);
    }
}




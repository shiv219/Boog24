package com.boog24.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boog24.R;
import com.boog24.extra.Utils;
import com.boog24.modals.getSaloonDetail.Service;

import java.util.List;

public class SalonDetailFooterAdapter extends RecyclerView.Adapter<SalonDetailFooterAdapter.ViewHolder> {

    Context context;

    List<Service> services;

    public SalonDetailFooterAdapter(Context context) {
        this.context=context;
    }

    public SalonDetailFooterAdapter(Context context, List<Service> services) {
        this.context=context;
        this.services=services;
    }


    @NonNull
    @Override
    public SalonDetailFooterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_detail_footer_row, parent, false);
        return new SalonDetailFooterAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SalonDetailFooterAdapter.ViewHolder holder, int position) {


        holder.txtTitle.setText(services.get(position).getServiceName());
        holder.txt_time.setText(services.get(position).getHour() + " " + context.getResources().getString(R.string.hour) + " " + services.get(position).getMinutes() + " " + context.getResources().getString(R.string.minutes));
        holder.tvPrice.setText("€" + Utils.getFormatedDouble(services.get(position).getServicePrice()));


        if (services.get(position).getSelected()) {
            holder.btnSelect.setText(context.getResources().getString(R.string.remove));
        } else {
            holder.btnSelect.setText(context.getResources().getString(R.string.select));
        }

        Log.e("TAG", "onBindViewHolder: " + services.get(position).getSelected());

        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                services.get(position).setSelected(!services.get(position).getSelected());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txt_time,tvPrice;
        Button btnSelect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txt_time=itemView.findViewById(R.id.txt_time);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            btnSelect=itemView.findViewById(R.id.btnSelect);
        }
    }
}



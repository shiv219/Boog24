package com.boog24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.modals.getSaloonDetail.SalonService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SalonDetailHeaderAdapter extends RecyclerView.Adapter<SalonDetailHeaderAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;

    boolean slection = true;
    List<SalonService> salonServices;


    public SalonDetailHeaderAdapter(Context context, List<SalonService> salonServices) {
        this.context=context;
        this.salonServices=salonServices;
    }

    @NonNull
    @Override
    public SalonDetailHeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_detail_header_row, parent, false);
        return new SalonDetailHeaderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonDetailHeaderAdapter.ViewHolder holder, int position) {


        holder.txt_header_name.setText(salonServices.get(position).getSubCategoryName());
        SalonDetailFooterAdapter headerAdapter = new SalonDetailFooterAdapter(context,salonServices.get(position).getServices());
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        holder.recyclerView.setAdapter(headerAdapter);
        holder.recyclerView.setNestedScrollingEnabled(true);

        holder.recyclerView.setVisibility(View.GONE);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.img_arrow_down.getVisibility()==View.VISIBLE){
                    holder.recyclerView.setVisibility(View.VISIBLE);
                    holder.img_arrow_down.setVisibility(View.GONE);
                    holder.img_arrow_up.setVisibility(View.VISIBLE);
                }else{
                    holder.recyclerView.setVisibility(View.GONE);
                    holder.img_arrow_up.setVisibility(View.GONE);
                    holder.img_arrow_down.setVisibility(View.VISIBLE);
                }
            }
        });

//        holder.img_arrow_down.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                holder.txt_header_name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
//                holder.recyclerView.setVisibility(View.VISIBLE);
//                holder.img_arrow_down.setVisibility(View.GONE);
//                holder.img_arrow_up.setVisibility(View.VISIBLE);
//
//            }
//        });
//
//
//        holder.img_arrow_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                holder.txt_header_name.setTextColor(context.getResources().getColor(R.color.blackish));
//                holder.recyclerView.setVisibility(View.GONE);
//                holder.img_arrow_up.setVisibility(View.GONE);
//                holder.img_arrow_down.setVisibility(View.VISIBLE);
//            }
//        });
//

    }

    @Override
    public int getItemCount() {
        return salonServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_header_name;
        ImageView img_arrow_down, img_arrow_up;
        LinearLayout lytFooter;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_header_name = itemView.findViewById(R.id.txt_header_name);
            img_arrow_down = itemView.findViewById(R.id.img_arrow_down);
            img_arrow_up = itemView.findViewById(R.id.img_arrow_up);
            lytFooter = itemView.findViewById(R.id.lytFooter);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}


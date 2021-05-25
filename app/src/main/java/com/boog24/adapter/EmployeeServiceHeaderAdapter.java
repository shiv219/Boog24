package com.boog24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.modals.getSaloonDetail.SalonService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeServiceHeaderAdapter extends RecyclerView.Adapter<EmployeeServiceHeaderAdapter.ViewHolder> {

    Context context;
    String[] pakageType;
    String[] packageRate;

    boolean slection = true;
    List<SalonService> salonServices;


    public EmployeeServiceHeaderAdapter(Context context, List<SalonService> salonServices) {
        this.context=context;
        this.salonServices=salonServices;
    }

    @NonNull
    @Override
    public EmployeeServiceHeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salon_detail_header_row, parent, false);
        return new EmployeeServiceHeaderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeServiceHeaderAdapter.ViewHolder holder, int position) {


        holder.txt_header_name.setVisibility(View.GONE);
        holder.img_arrow_down.setVisibility(View.GONE);
        holder.img_arrow_up.setVisibility(View.GONE);
        holder.lytFooter.setVisibility(View.GONE);
        holder.relativeLayout.setVisibility(View.GONE);
        holder.lyt.setBackgroundColor(context.getResources().getColor(R.color.white));

//        if (salonServices.get(position).getServices().get(0).getSelected())
//        EmployeeServicesAdapter headerAdapter = new EmployeeServicesAdapter(context, salonServices.get(position).getServices());
//        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        holder.recyclerView.setAdapter(headerAdapter);
//        holder.recyclerView.setNestedScrollingEnabled(true);


    }
    @Override
    public int getItemCount() {
        return salonServices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_header_name;
        ImageView img_arrow_down, img_arrow_up;
        LinearLayout lytFooter,lyt;
        RelativeLayout relativeLayout;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_header_name = itemView.findViewById(R.id.txt_header_name);
            img_arrow_down = itemView.findViewById(R.id.img_arrow_down);
            img_arrow_up = itemView.findViewById(R.id.img_arrow_up);
            lytFooter = itemView.findViewById(R.id.lytFooter);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            relativeLayout = itemView.findViewById(R.id.rlt);
            lyt = itemView.findViewById(R.id.lyt);
        }
    }
}


package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boog24.MyApplication;
import com.boog24.R;
import com.boog24.activity.ContactDetailActivity;
import com.boog24.modals.getSaloonDetail.SalonService;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EmployeeServicesAdapter extends RecyclerView.Adapter<EmployeeServicesAdapter.ViewHolder> {

    Context context;
    ArrayList<SalonService> services;
    JSONArray jsonArray;
    String what;
    List<com.boog24.modals.getBookingDetail.SalonService> salonServices;




    public EmployeeServicesAdapter(Context context, JSONArray jsonArray,String what) {
        this.context=context;
        this.jsonArray=jsonArray;
        this.what=what;
    }

    public EmployeeServicesAdapter(Context context, List<com.boog24.modals.getBookingDetail.SalonService> salonServices,String what) {
    this.context=context;
    this.salonServices=salonServices;
    this.what=what;
    }



    @NonNull
    @Override
    public EmployeeServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.employee_service_row, parent, false);
        return new EmployeeServicesAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeServicesAdapter.ViewHolder holder, int position) {

        if (jsonArray!=null) {
            if (what.equalsIgnoreCase("contact"))
                holder.btnSelect.setVisibility(View.VISIBLE);
            else
                holder.btnSelect.setVisibility(View.GONE);

            try {
                if(jsonArray.getJSONObject(position).getString("hour").equals("00")){
                    holder.txt_time.setText(jsonArray.getJSONObject(position).getString("minutes")+" " + context.getResources().getString(R.string.minutes));
                }else{
                    int hour = (jsonArray.getJSONObject(position).getInt("hour"));
                    if(hour > 1){
                        holder.txt_time.setText(jsonArray.getJSONObject(position).getString("hour") + " "+context.getResources().getString(R.string.hours)+" " + jsonArray.getJSONObject(position).getString("minutes")+" " + context.getResources().getString(R.string.minutes));
                    }else{
                        holder.txt_time.setText(jsonArray.getJSONObject(position).getString("hour") + " "+context.getResources().getString(R.string.hour)+" " + jsonArray.getJSONObject(position).getString("minutes")+" " + context.getResources().getString(R.string.minutes));
                    }
                }

                holder.txtTitle.setText(jsonArray.getJSONObject(position).getString("name"));
                holder.tvPrice.setText("€" + jsonArray.getJSONObject(position).getString("price"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            holder.btnSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


//                for (int j=0;j<services.size();j++) {
//                    for (int k=0;k<services.get(j).getServices().size();k++){
//
//                        try {
//                            if (jsonArray.getJSONObject(position).getString("service_id").equalsIgnoreCase(services.get(j).getServices().get(k).getServiceId())) {
//                                services.get(j).getServices().get(k).setSelected(false);
//                                break;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                }

//                for (int i=0;i<jsonArray.length();i++){
//
//                    try {
//                        if (jsonArray.getJSONObject(position).getString("service_id").equalsIgnoreCase(jsonArray.getJSONObject(i).getString("service_id"))){
//                            jsonArray.remove(position);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }

                    jsonArray.remove(position);

                    if (jsonArray.length() > 0)
                        MyApplication.getInstance().getSession().setData(jsonArray.toString());
                    else
                        MyApplication.getInstance().getSession().setData("");


//            chooseEmployeesActivity.refresh(services);

                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    ((ContactDetailActivity) context).setAmount();
                    ((ContactDetailActivity) context).back();
                    Log.e("TAG", "onClick: " + jsonArray.toString());


//                    intent.putExtra("salonId",)
                }
            });
        }else{

            if (what.equalsIgnoreCase("contact"))
                holder.btnSelect.setVisibility(View.VISIBLE);
            else
                holder.btnSelect.setVisibility(View.GONE);

                holder.txt_time.setText(salonServices.get(position).getHour() + " hour " + salonServices.get(position).getMinutes() + " minutes");
                holder.txtTitle.setText(salonServices.get(position).getServiceName());
                holder.tvPrice.setText("€" + salonServices.get(position).getServicePrice());

        }
        }


    @Override
    public int getItemCount() {
        return jsonArray!=null?jsonArray.length():salonServices.size();
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




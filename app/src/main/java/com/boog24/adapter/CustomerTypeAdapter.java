package com.boog24.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.ChooseEmployeesActivity;
import com.boog24.activity.RecommendSalonActivity;
import com.boog24.modals.employeeList.EmployeeDatum;
import com.boog24.modals.getCitiesList.CityDatum;

import java.util.ArrayList;

public class CustomerTypeAdapter extends ArrayAdapter<EmployeeDatum> {
    LayoutInflater inflater;
    ArrayList<EmployeeDatum> objects;
    ViewHolder holder = null;
    ChooseEmployeesActivity signUpActivity;
    RecommendSalonActivity recommendSalonActivity;
    ArrayList<CityDatum> customerTypeDatumArrayList;


    public CustomerTypeAdapter(Context context, int textViewResourceId, ArrayList<EmployeeDatum> objects, ChooseEmployeesActivity signUpActivity) {
        super(context, textViewResourceId, objects);
        inflater = ((Activity) context).getLayoutInflater();
        this.objects = objects;
        this.signUpActivity = signUpActivity;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(final int position, View convertView, ViewGroup parent) {
        EmployeeDatum listItemAddProg = objects.get(position);
        View row = convertView;
        if (null == row) {
            holder = new ViewHolder();
            row = inflater.inflate(R.layout.spinner_item_row, parent, false);

            holder.ctvTitle = (TextView) row.findViewById(R.id.ctvTitle);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

            if (objects!=null)
            holder.ctvTitle.setText(listItemAddProg.getEmployeename());
        return row;
    }

    static class ViewHolder {
        TextView ctvTitle;
        ImageView img;
    }
}



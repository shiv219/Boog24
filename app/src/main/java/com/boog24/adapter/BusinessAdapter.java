package com.boog24.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.ChooseEmployeesActivity;
import com.boog24.activity.RecommendSalonActivity;
import com.boog24.modals.getCitiesList.CategoryDatum;

import java.util.ArrayList;

public class BusinessAdapter extends ArrayAdapter<CategoryDatum> {
    LayoutInflater inflater;
    CustomerTypeAdapter.ViewHolder holder = null;
    ChooseEmployeesActivity signUpActivity;
    RecommendSalonActivity recommendSalonActivity;
    ArrayList<CategoryDatum> customerTypeDatumArrayList;



    public BusinessAdapter(RecommendSalonActivity recommendSalonActivity, int spinner_item_row, ArrayList<CategoryDatum> customerTypeDatumArrayList) {
        super(recommendSalonActivity,spinner_item_row,customerTypeDatumArrayList);
        inflater = ((Activity) recommendSalonActivity).getLayoutInflater();
        this.recommendSalonActivity=recommendSalonActivity;
        this.customerTypeDatumArrayList=customerTypeDatumArrayList;

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
        CategoryDatum listItemAddProg = customerTypeDatumArrayList.get(position);
        View row = convertView;
        if (null == row) {
            holder = new CustomerTypeAdapter.ViewHolder();
            row = inflater.inflate(R.layout.spinner_item_row, parent, false);

            holder.ctvTitle = (TextView) row.findViewById(R.id.ctvTitle);
            row.setTag(holder);
        } else {
            holder = (CustomerTypeAdapter.ViewHolder) row.getTag();
        }

        if (customerTypeDatumArrayList!=null)
            holder.ctvTitle.setText(listItemAddProg.getCategoryName());
        return row;
    }

    static class ViewHolder {
        TextView ctvTitle;
        ImageView img;
    }
}


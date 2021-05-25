package com.boog24.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.activity.SalonListingActivity;
import com.boog24.modals.SubCategory;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllCategoriesItemAdapter extends RecyclerView.Adapter<AllCategoriesItemAdapter.ViewHolder> {

    Context context;
    List<SubCategory> categoryData;
    String category_id;

    public AllCategoriesItemAdapter(Context context, List<SubCategory> categoryData,String category_id) {
        this.context=context;
        this.categoryData=categoryData;
        this.category_id=category_id;
    }

    @NonNull
    @Override
    public AllCategoriesItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_categories_item_row, parent, false);
        return new AllCategoriesItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesItemAdapter.ViewHolder holder, int position) {

        holder.tvname.setText(categoryData.get(position).getSubCategoryName());
//        Glide.with(context).load(categoryData.get(position).getCategoryImage()).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SalonListingActivity.class);
                intent.putExtra("sub_category_id",categoryData.get(position).getSubCategoryId());
                intent.putExtra("category_id",category_id);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvname);
        }
    }
}



package com.boog24.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.modals.CategoryDatum;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllCategoriesHeaderAdapter extends RecyclerView.Adapter<AllCategoriesHeaderAdapter.ViewHolder> {

    Context context;
    List<CategoryDatum> categoryData;


    public AllCategoriesHeaderAdapter(Context context, List<CategoryDatum> categoryData) {
        this.context=context;
        this.categoryData=categoryData;
    }

    @NonNull
    @Override
    public AllCategoriesHeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.all_categories_header_row, parent, false);
        return new AllCategoriesHeaderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoriesHeaderAdapter.ViewHolder holder, int position) {

        holder.tvTitle.setText(categoryData.get(position).getCategoryName());
//        Glide.with(context).load(categoryData.get(position).getCategoryImage()).into(holder.image);

        AllCategoriesItemAdapter homeCategoriesAdapter = new AllCategoriesItemAdapter(context,categoryData.get(position).getSubCategory(),categoryData.get(position).getCategoryId());
        holder.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerview.setAdapter(homeCategoriesAdapter);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context, SalonListingActivity.class);
//                context.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerview;
        TextView tvTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            recyclerview=itemView.findViewById(R.id.recyclerview);
        }
    }
}


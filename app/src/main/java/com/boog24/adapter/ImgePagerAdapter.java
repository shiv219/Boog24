package com.boog24.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boog24.R;
import com.boog24.modals.getSaloonDetail.SalonImage;
import com.boog24.modals.homescreen.BannerDatum;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ImgePagerAdapter extends PagerAdapter {

    private Context activity;
    List<BannerDatum> bannerData;
    List<SalonImage> salonImages;
    public ImgePagerAdapter(Context activity, List<BannerDatum> bannerData) {
        this.activity=activity;
        this.bannerData=bannerData;
    }
    public ImgePagerAdapter(Context activity, List<SalonImage> salonImages,String detail) {
        this.activity=activity;
        this.salonImages=salonImages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater inflater = ((Activity)activity).getLayoutInflater();

        View viewItem = inflater.inflate(R.layout.image_items, container, false);
        RoundedImageView imageView =  viewItem.findViewById(R.id.img);
        TextView text =  viewItem.findViewById(R.id.text);

        if (bannerData!=null) {
            Picasso.get().load(bannerData.get(position).getBannerImage())
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .into(imageView);

        }else{
            Picasso.get().load(salonImages.get(position).getSalonImage())
                    .error(R.drawable.logo)
                    .placeholder(R.drawable.logo)
                    .into(imageView);
        }

        ((ViewPager)container).addView(viewItem);
        return viewItem;
    }

    @Override
    public int getCount() {
        return bannerData!=null ? bannerData.size() : salonImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        // TODO Auto-generated method stub
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
}




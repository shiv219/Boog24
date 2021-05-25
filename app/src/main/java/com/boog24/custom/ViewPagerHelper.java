package com.boog24.custom;

import android.content.Context;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ViewPagerHelper {

    public static void custPagerTransformer(Context context, ViewPager viewPager){
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(14);
        viewPager.setPadding(58, 20, 55, 20);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            private int maxTranslateOffsetX =  dp2px(context, 120);;

            @Override
            public void transformPage(View view, float position) {

                int leftInScreen = view.getLeft() - viewPager.getScrollX();
                int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
                int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
                float offsetRate = (float) offsetX * 0.15f / viewPager.getMeasuredWidth();
                float scaleFactor = 1 - Math.abs(offsetRate);
                if (scaleFactor > 0) {
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    view.setTranslationX(-maxTranslateOffsetX * offsetRate);
                }
            }
        });
    }

    public static void PagerTransformer(Context context, ViewPager viewPager){
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(24);
        viewPager.setPadding(180, 0, 180, 0);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            private int maxTranslateOffsetX =  dp2px(context, 120);;

            @Override
            public void transformPage(View view, float position) {

                int leftInScreen = view.getLeft() - viewPager.getScrollX();
                int centerXInViewPager = leftInScreen + view.getMeasuredWidth() / 2;
                int offsetX = centerXInViewPager - viewPager.getMeasuredWidth() / 2;
                float offsetRate = (float) offsetX * 0.38f / viewPager.getMeasuredWidth();
                float scaleFactor = 1 - Math.abs(offsetRate);
                if (scaleFactor > 0) {
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                    view.setTranslationX(-maxTranslateOffsetX * offsetRate);
                }
            }
        });
    }

    private static int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }


    public static void trasform(final ViewPager viewPager){
        viewPager.setClipToPadding(false);
        viewPager.setPageMargin(24);
        viewPager.setPadding(80, 0, 80, 0);
        viewPager.setOffscreenPageLimit(3);

        viewPager.setPageTransformer(false, new ViewPager.PageTransformer()
        {
            @Override
            public void transformPage(View page, float position)
            {
                int pageWidth = viewPager.getMeasuredWidth() - viewPager.getPaddingLeft() - viewPager.getPaddingRight();
                int pageHeight = viewPager.getHeight();
                int paddingLeft = viewPager.getPaddingLeft();
                float transformPos = (float) (page.getLeft() - (viewPager.getScrollX() + paddingLeft)) / pageWidth;

                int max = pageHeight / 10;
                if (transformPos < -1)
                {
                    // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0.5f);// to make left transparent
                    page.setScaleY(0.7f);
                }
                else if (transformPos <= 1)
                {
                    // [-1,1]
                    page.setAlpha(1f);
                    page.setScaleY(1f);
                }
                else
                {
                    // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0.5f);// to make right transparent
                    page.setScaleY(0.7f);
                }
            }
        });
    }
}

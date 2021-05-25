package com.boog24.view;


import com.boog24.modals.getBookingDetail.Result;

public interface IBookingView extends IView {
    void onGetDetail(Result response);
}

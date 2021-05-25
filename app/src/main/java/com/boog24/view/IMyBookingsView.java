package com.boog24.view;


import com.boog24.modals.myBookings.Result;

public interface IMyBookingsView extends IView {
    void onGetDetail(Result response);
}

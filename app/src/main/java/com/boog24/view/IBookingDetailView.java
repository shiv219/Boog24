package com.boog24.view;

import com.boog24.modals.bookingAppointment.Result;

public interface IBookingDetailView extends IView {
    void onGetDetail(Result response);
    void onGetUpdateDetail(Result response);
}


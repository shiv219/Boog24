package com.boog24.view;

import android.content.Context;

public interface IView {
    Context getContext();
    void enableLoadingBar(Context context, boolean enable, String s);

    void onError(String reason);
}

package com.boog24.presenter;


import com.boog24.view.IView;

public abstract class BasePresenter<I extends IView> {

    private I iView;
    BasePresenter() { }
    public I getView() {
        return iView;
    }
    public void setView(I iView) {
        this.iView = iView;
    }
    public boolean handleError(retrofit2.Response response) {
        /*if (response.code() == 203) {
           // return handleError(((BaseResponse) response.body()), false);
        }
        else if (response.code() == 440) {
            getView().onTokenExpired();
            return true;
        }
        else */
        if (response.errorBody() != null) {
            try {
                String error = response.errorBody().string();
                getView().onError(response != null ? error : null);
            }
            catch (Exception e) {
                e.printStackTrace();
                getView().onError(null);
                return true;
            }
            return true;
        }
        return false;
    }

}

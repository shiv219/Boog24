package com.boog24.api;


import com.boog24.modals.CommonOffset;
import com.boog24.modals.homescreen.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @Multipart
    @POST("userSignin")
    Call<CommonOffset> userSignin(@Header("Access-Token") String access_token,
                                  @Header("Lang") String Lang,
                                  @Part("emailid") String emailid,
                                  @Part("password") String password,
                                  @Part("device_id") String device_id,
                                  @Part("fcm_id") String fcm_id,
                                  @Part("device_type") String device_type
    );

    @Multipart
    @POST("userSignup")
    Call<CommonOffset> userSignup(@Header("Access-Token") String access_token,
                                  @Header("Lang") String Lang,
                                  @Part("emailid") String emailid,
                                  @Part("firstname") String firstname,
                                  @Part("lastname") String lastname,
                                  @Part("phone_number") String phone_number,
                                  @Part("password") String password,
                                  @Part("confirm_password") String confirm_password,
                                  @Part("gender") String gender,
                                  @Part("country_code") String countryCode
    );

    @Multipart
    @POST("forgotPassword")
    Call<CommonOffset> forgotPassword(@Header("Access-Token") String access_token,
                                      @Header("Lang") String Lang,
                                      @Part("email_id") String emailid
    );

    @POST("getCategories")
    Call<CommonOffset> getCategories(@Header("Access-Token") String access_token,
                                     @Header("Login-Key") String Login_Key,
                                     @Header("Lang") String Lang

    );

    @POST("logout")
    Call<CommonOffset> logout(@Header("Access-Token") String access_token,
                              @Header("Login-Key") String Login_Key,
                              @Header("Lang") String Lang

    );

    @POST("getContactUsDetails")
    Call<CommonOffset> getContactUsDetails(@Header("Access-Token") String access_token,
                                           @Header("Login-Key") String Login_Key,
                                           @Header("Lang") String Lang

    );

    @POST("homeScreen")
    Call<Result> homeScreen(@Header("Access-Token") String access_token,
                            @Header("Login-Key") String Login_Key,
                            @Header("Lang") String Lang

    );

    @Multipart
    @POST("getSaloons")
    Call<com.boog24.modals.getSaloons.Result> getSaloons(@Header("Access-Token") String access_token,
                                                         @Header("Login-Key") String Login_Key,
                                                         @Header("Lang") String Lang,
                                                         @Part("list_type") String list_type,
                                                         @Part("category_id") String category_id,
                                                         @Part("sub_category_id") String sub_category_id,
                                                         @Part("latitude") String latitude,
                                                         @Part("longitude") String longitude,
                                                         @Part("search_string") String search_string,
                                                         @Part("sort_by") String sort_by

    );

    @Multipart
    @POST("getSaloonDetails")
    Call<com.boog24.modals.getSaloonDetail.Result> getSaloonDetails(@Header("Access-Token") String access_token,
                                                                    @Header("Login-Key") String Login_Key,
                                                                    @Header("Lang") String Lang,
                                                                    @Part("salon_id") String salon_id

    );


    @Multipart
    @POST("getEmployeesList")
    Call<com.boog24.modals.employeeList.Result> getEmployeesList(@Header("Access-Token") String access_token,
                                                                 @Header("Lang") String Lang,
                                                                 @Part("salon_id") String salon_id
    );

    @Multipart
    @POST("addToWishlist")
    Call<com.boog24.modals.addWishlist.Result> addToWishlist(@Header("Access-Token") String access_token,
                                                             @Header("Login-Key") String Login_Key,
                                                             @Header("Lang") String Lang,
                                                             @Part("salon_id") String salon_id

    );

    @POST("getWishlist")
    Call<com.boog24.modals.getWishlist.Result> getWishlist(@Header("Access-Token") String access_token,
                                                           @Header("Login-Key") String Login_Key,
                                                           @Header("Lang") String Lang

    );

    @Multipart
    @POST("updateProfileImage")
    Call<CommonOffset> updateProfileImage(
            @Header("Access-Token") String access_token,
            @Header("Login-Key") String Login_Key,
            @Header("Lang") String Lang,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("updateProfile")
    Call<com.boog24.modals.updateProfile.Result> updateProfile(@Header("Access-Token") String access_token,
                                                               @Header("Login-Key") String Login_Key,
                                                               @Header("Lang") String Lang,
                                                               @Part("firstname") String firstname,
                                                               @Part("lastname") String lastname,
                                                               @Part("phone_number") String phone_number,
                                                               @Part("gender") String gender,
                                                               @Part("country_code") String countryCode

    );


    @Multipart
    @POST("bookingAppointment")
    Call<com.boog24.modals.bookingAppointment.Result> bookingAppointment(@Header("Access-Token") String access_token,
                                                                         @Header("Login-Key") String Login_Key,
                                                                         @Header("Lang") String Lang,
                                                                         @Part("customer_first_name") String customer_first_name,
                                                                         @Part("customer_last_name") String customer_last_name,
                                                                         @Part("customer_email") String customer_email,
                                                                         @Part("customer_phone_number") String customer_phone_number,
                                                                         @Part("services_id") String services_id,
                                                                         @Part("employ_id") String employ_id,
                                                                         @Part("date") String date_time,
                                                                         @Part("salon_id") String salon_id,
                                                                         @Part("total_amount") String total_amount,
                                                                         @Part("start_time") String start_time,
                                                                         @Part("end_time") String end_time

    );

    @Multipart
    @POST("updateBookingAppointment")
    Call<com.boog24.modals.bookingAppointment.Result> updateBookingAppointment(@Header("Access-Token") String access_token,
                                                                               @Header("Login-Key") String Login_Key,
                                                                               @Header("Lang") String Lang,
                                                                               @Part("customer_first_name") String customer_first_name,
                                                                               @Part("customer_last_name") String customer_last_name,
                                                                               @Part("customer_email") String customer_email,
                                                                               @Part("customer_phone_number") String customer_phone_number,
                                                                               @Part("services_id") String services_id,
                                                                               @Part("employ_id") String employ_id,
                                                                               @Part("date") String date_time,
                                                                               @Part("salon_id") String salon_id,
                                                                               @Part("total_amount") String total_amount,
                                                                               @Part("start_time") String start_time,
                                                                               @Part("end_time") String end_time,
                                                                               @Part("order_id") String order_id,
                                                                               @Part("user_notification_id") String user_notification_id

    );


    @Multipart
    @POST("myBookings")
    Call<com.boog24.modals.myBookings.Result> myBookings(@Header("Access-Token") String access_token,
                                                         @Header("Login-Key") String Login_Key,
                                                         @Header("Lang") String Lang,
                                                         @Part("list_type") String list_type

    );

    @Multipart
    @POST("addFeedBack")
    Call<CommonOffset> addFeedBack(@Header("Access-Token") String access_token,
                                   @Header("Login-Key") String Login_Key,
                                   @Header("Lang") String Lang,
                                   @Part("order_id") String order_id,
                                   @Part("salon_id") String salon_id,
                                   @Part("rating") String rating,
                                   @Part("review_message") String review_message

    );

    @Multipart
    @POST("cancelBooking")
    Call<CommonOffset> cancelBooking(@Header("Access-Token") String access_token,
                                     @Header("Login-Key") String Login_Key,
                                     @Header("Lang") String Lang,
                                     @Part("order_id") String order_id


    );

    @Multipart
    @POST("gatBookingDetails")
    Call<com.boog24.modals.getBookingDetail.Result> gatBookingDetails(@Header("Access-Token") String access_token,
                                                                      @Header("Login-Key") String Login_Key,
                                                                      @Header("Lang") String Lang,
                                                                      @Part("order_id") String order_id


    );

    @Multipart
    @POST("checkSalonAvailability")
    Call<CommonOffset> checkSalonAvailability(@Header("Access-Token") String access_token,
                                              @Header("Login-Key") String Login_Key,
                                              @Header("Lang") String Lang,
                                              @Part("employ_id") String employ_id,
                                              @Part("date_time") String date_time,
                                              @Part("salon_id") String salon_id


    );

    @GET("getCitiesList")
    Call<com.boog24.modals.getCitiesList.Result> getCitiesList(@Header("Access-Token") String access_token,
                                                               @Header("Lang") String Lang
    );

    @Multipart
    @POST("recommend_salon")
    Call<CommonOffset> recommend_salon(@Header("Access-Token") String access_token,
                                       @Header("Lang") String Lang,
                                       @Part("user_email") String user_email,
                                       @Part("first_name") String first_name,
                                       @Part("last_name") String last_name,
                                       @Part("contact_number") String contact_number,
                                       @Part("shopname") String shopname,
                                       @Part("street") String street,
                                       @Part("city_name") String city_name,
                                       @Part("speak_about_site") String speak_about_site,
                                       @Part("category_id") String category_id,
                                       @Part("user_id") String user_id,
                                       @Part("zip_code") String zip_code,
                                       @Part("name_of_the_contact_person") String contactPerson


    );


    @Multipart
    @POST("getServiceTimeSlots")
    Call<com.boog24.modals.getServiceTimeSlots.Result> getServiceTimeSlots(@Header("Access-Token") String access_token,
                                                                           @Header("Login-Key") String Login_Key,
                                                                           @Header("Lang") String Lang,
                                                                           @Part("employee_id") String employee_id,
                                                                           @Part("date") String date_time,
                                                                           @Part("salon_id") String salon_id,
                                                                           @Part("services_id") String services_id

    );

    @Multipart
    @POST("changeNotificationStatus")
    Call<CommonOffset> updateNotificationStatus(@Header("Access-Token") String access_token,
                                                @Header("Login-Key") String Login_Key,
                                                @Header("Lang") String Lang,
                                                @Part("user_notification_status") String user_notification_status


    );

    @Multipart
    @POST("change-language")
    Call<CommonOffset> changeLanguage(@Header("Access-Token") String access_token,
                                      @Header("Login-Key") String Login_Key,
                                      @Header("Lang") String Lang,
                                      @Part("language") String language


    );

    @POST("getNotificationList")
    Call<com.boog24.modals.getNotification.Result> getNotificationList(@Header("Access-Token") String access_token,
                                                                       @Header("Login-Key") String Login_Key,
                                                                       @Header("Lang") String Lang
    );

    @POST("delete_account")
    Call<CommonOffset> deleteAccount(@Header("Access-Token") String access_token,
                                     @Header("Login-Key") String Login_Key,
                               @Header("Lang") String Lang
    );
}


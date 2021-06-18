package com.boog24.modals.getSaloonDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SaloonData implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("shopname")
    @Expose
    private String shopname;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("post_code")
    @Expose
    private String postCode;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("saloon_info")
    @Expose
    private String saloonInfo;
    @SerializedName("salon_images")
    @Expose
    private List<SalonImage> salonImages = null;
    @SerializedName("wishlist_status")
    @Expose
    private String wishlistStatus;
    @SerializedName("saloon_ratings")
    @Expose
    private String saloonRatings;
    @SerializedName("saloon_ratings_users")
    @Expose
    private String saloonRatingsUsers;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;
    @SerializedName("salon_services")
    @Expose
    private List<SalonService> salonServices = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSaloonInfo() {
        return saloonInfo;
    }

    public void setSaloonInfo(String saloonInfo) {
        this.saloonInfo = saloonInfo;
    }

    public List<SalonImage> getSalonImages() {
        return salonImages;
    }

    public void setSalonImages(List<SalonImage> salonImages) {
        this.salonImages = salonImages;
    }

    public String getWishlistStatus() {
        return wishlistStatus;
    }

    public void setWishlistStatus(String wishlistStatus) {
        this.wishlistStatus = wishlistStatus;
    }

    public String getSaloonRatings() {
        return saloonRatings;
    }

    public void setSaloonRatings(String saloonRatings) {
        this.saloonRatings = saloonRatings;
    }

    public String getSaloonRatingsUsers() {
        return saloonRatingsUsers;
    }

    public void setSaloonRatingsUsers(String saloonRatingsUsers) {
        this.saloonRatingsUsers = saloonRatingsUsers;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<SalonService> getSalonServices() {
        return salonServices;
    }

    public void setSalonServices(List<SalonService> salonServices) {
        this.salonServices = salonServices;
    }


    @SerializedName("salon_time_slot_list")
    @Expose
    private List<SalonTimeSlot> salonTimeSlotList = null;

    public List<SalonTimeSlot> getSalonTimeSlotList() {
        return salonTimeSlotList;
    }

    public void setSalonTimeSlotList(List<SalonTimeSlot> salonTimeSlotList) {
        this.salonTimeSlotList = salonTimeSlotList;
    }


    public class SalonTimeSlot {

        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("time_slots")
        @Expose
        private List<TimeSlot> timeSlots = null;

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public List<TimeSlot> getTimeSlots() {
            return timeSlots;
        }

        public void setTimeSlots(List<TimeSlot> timeSlots) {
            this.timeSlots = timeSlots;
        }

    }

    public class TimeSlot {

        @SerializedName("from_time")
        @Expose
        private String fromTime;
        @SerializedName("to_time")
        @Expose
        private String toTime;

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

    }
}
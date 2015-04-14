package com.dvhs.spark.models;

import com.dvhs.spark.adapters.CommentsAdapter;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by kaan on 2/14/15.
 */
@ParseClassName("Attraction")
public class Attraction extends ParseObject {

    public Attraction() {

    }

    public String generateMapURL() {
        String fixedAddress = getAddress().replace(' ', '+');
        fixedAddress += "+Gilbert+Arizona";

        return "https://maps.googleapis.com/maps/api/staticmap?center=" + fixedAddress +
                "+&zoom=16&size=800x400&markers=color:blue%7Clabel:S%7C"+ fixedAddress +
                "&sensor=false";
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String address) {
        put("address", address);
    }

    public String getWebsite() {
        return getString("website");
    }

    public void setWebsite(String website) {
        put("website", website);
    }

    public String getPhone() {
        return getString("phone");
    }

    public void setPhone(String phone) {
        put("phone", phone);
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public boolean getDefault() {
        return getBoolean("default");
    }

    public void setDefault(boolean isDefault) {
        put("default", isDefault);
    }

    public String getType() {
        return getString("type");
    }

    public void setType(String type) {
        put("type", type);
    }

    public int getRatingCount() {
        return getInt("ratingCount");
    }

    public void addToRatingTotal(int rating) {
        increment("ratingTotal", rating);
        increment("ratingCount");
    }

    public int getRatingTotal() {
        return getInt("ratingTotal");
    }

}

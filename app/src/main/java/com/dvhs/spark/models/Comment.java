package com.dvhs.spark.models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;

/**
 * Created by kaan on 3/19/15.
 */

@ParseClassName("Comment")
public class Comment extends ParseObject {

    public Comment() {

    }

    public Comment(String text, int rating) {
        put("text", text);
        put("rating", rating);
    }

    public String getText() {
        return getString("text");
    }

    public int getRating() {
        return getInt("rating");
    }

    public ParseUser getUser() {
        return getParseUser("user");
    }

    public String getUserProfileImageURL() {
        try {
            return "https://graph.facebook.com/" + getUser().fetchIfNeeded().getString("facebookId") + "/picture?type=normal";
        } catch(com.parse.ParseException e) {
            Log.e("MyApp", "Error retrieving Facebook profile URL.");
            return null;
        }
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public void setText(String text) {
        put("text", text);
    }

    public void setRating(int rating) {
        put("rating", rating);
    }

    public void setAttraction(Attraction attraction) {
        put("attraction", attraction);
    }

    public Attraction getAttraction() {
        return (Attraction) get("attraction");
    }
}

package com.dvhs.spark.extras;

import android.app.Application;

import com.dvhs.spark.models.Attraction;
import com.dvhs.spark.models.Comment;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by kaan on 3/21/15.
 */
public class SparkApplication extends Application {

    public static final String APPLICATION_ID = "2hAxedMyTqViKS6oPpZebOq0s9cbDD6t0rObGGms";
    public static final String CLIENT_ID = "U63C5qbhDscLB75G91aJPpLmxYM4BHT8CVOCe3Se";

    public static final String FACEBOOK_ID = "827089167346411";

    @Override
    public void onCreate() {
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Attraction.class);
        Parse.initialize(this, APPLICATION_ID, CLIENT_ID);
        ParseFacebookUtils.initialize(FACEBOOK_ID);

    }
}
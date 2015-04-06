package com.dvhs.spark.extras;

import android.content.Context;
import android.util.Log;

import com.dvhs.spark.models.Attraction;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaan on 2/14/15.
 */
public class AttractionParser {

    private Context context;
    public static boolean SAVE_TO_PARSE = false;

    public AttractionParser(Context context) {
        this.context = context;
    }

    public List<Attraction> parseAttractionsFromFile(String fileName) {
        List<Attraction> attractions = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String text = new String(buffer);

            JSONArray data = new JSONObject(text).getJSONArray("result");
            attractions = new ArrayList<Attraction>();

            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.getJSONObject(i);

                String name = item.optString("CompanyName");
                if (!name.equals("")) {
                    Attraction a = new Attraction();
                    a.setName(name);
                    a.setPhone(item.optString("Phone"));
                    a.setWebsite(item.optString("WebAddress"));
                    a.setAddress(item.optString("AddressNumber") + " "
                            + item.optString("AddressDirection") + " "
                            + item.optString("AddressStreet") + " "
                            + item.optString("StreetType"));

                    a.setDefault(true);
                    a.setType(fileName.replace(".txt", ""));

                    if (SAVE_TO_PARSE) {
                        a.saveInBackground();
                    }

                    attractions.add(a);
                }
            }

        } catch (IOException e) {
            Log.e("AttractionParser", e.getMessage());
            return null;
        } catch (JSONException e) {
            Log.e("AttractionParser", e.getMessage());
            return null;
        }
        return attractions;
    }

    public ParseQuery<Attraction> generateAttractionQuery(String attractionType) {
        ArrayList<Attraction> attractions = new ArrayList<Attraction>();
        ParseQuery<Attraction> query = new ParseQuery<Attraction>("Attraction");

        query.whereEqualTo("type", attractionType);

        return query;
    }

    //Make sure SAVE_TO_PARSE = true before running this method.
    public void sendAllAttractionsToParse() {
        parseAttractionsFromFile("heritage_district.txt");
        parseAttractionsFromFile("hotels.txt");
        parseAttractionsFromFile("points_of_interest.txt");
        parseAttractionsFromFile("restaurants.txt");
    }
}

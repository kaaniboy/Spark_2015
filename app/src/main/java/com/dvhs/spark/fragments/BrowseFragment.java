package com.dvhs.spark.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dvhs.spark.extras.AttractionComparator;
import com.dvhs.spark.extras.AttractionParser;
import com.dvhs.spark.activities.MainActivity;
import com.dvhs.spark.R;
import com.dvhs.spark.adapters.AttractionsAdapter;
import com.dvhs.spark.models.Attraction;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BrowseFragment extends Fragment {

    RecyclerView recyclerView;
    TextView textNoConnection;
    AttractionsAdapter adapter;
    MainActivity mainActivity;
    Toolbar toolbar;
    String cleanedAttractionType = "heritage_district";
    String prettyAttractionType = "Heritage District";

    public void setToolbar(Toolbar t) {
        toolbar = t;
    }

    public void setMainActivity(MainActivity a) {
        mainActivity = a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_attractions);
        textNoConnection = (TextView) view.findViewById(R.id.text_no_connection);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        AttractionParser parser = new AttractionParser(getActivity().getApplicationContext());
        ParseQuery<Attraction> query = parser.generateAttractionQuery(cleanedAttractionType);

        if (toolbar != null) {
            toolbar.setTitle(prettyAttractionType);
        }

        query.findInBackground(new FindCallback<Attraction>() {
            @Override
            public void done(List<Attraction> attractions, ParseException e) {

                if (attractions != null) {
                    textNoConnection.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Collections.sort(attractions, new AttractionComparator());
                    adapter = new AttractionsAdapter(mainActivity, attractions);
                    recyclerView.setAdapter(adapter);
                } else {
                    textNoConnection.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }

    public void switchAttractionType(String type) {
        AttractionParser parser = new AttractionParser(mainActivity.getApplicationContext());

        if (type.equals("Heritage District")) {
            cleanedAttractionType = "heritage_district";
            prettyAttractionType = "Heritage District";
            toolbar.setTitle("Heritage District");

        } else if (type.equals("Hotels")) {
            cleanedAttractionType = "hotels";
            prettyAttractionType = "Hotels";
            toolbar.setTitle("Hotels");

        } else if (type.equals("Points of Interest")) {
            cleanedAttractionType = "points_of_interest";
            prettyAttractionType = "Points of Interest";
            toolbar.setTitle("Points of Interest");

        } else if (type.equals("Restaurants")) {
            cleanedAttractionType = "restaurants";
            prettyAttractionType = "Restaurants";
            toolbar.setTitle("Restaurants");
        }

        ParseQuery<Attraction> query = parser.generateAttractionQuery(cleanedAttractionType);
        query.findInBackground(new FindCallback<Attraction>() {
            @Override
            public void done(List<Attraction> attractions, ParseException e) {
                if (attractions != null) {
                    textNoConnection.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Collections.sort(attractions, new AttractionComparator());
                    adapter = new AttractionsAdapter(mainActivity, attractions);
                    recyclerView.setAdapter(adapter);
                } else {
                    textNoConnection.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }
}

package com.dvhs.spark.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dvhs.spark.extras.AttractionParser;
import com.dvhs.spark.activities.MainActivity;
import com.dvhs.spark.R;
import com.dvhs.spark.adapters.AttractionsAdapter;
import com.dvhs.spark.models.Attraction;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseFragment extends Fragment {

    RecyclerView recyclerView;
    AttractionsAdapter adapter;
    List<Attraction> attractions;
    MainActivity mainActivity;
    Toolbar toolbar;

    public void setToolbar(Toolbar t) {
        toolbar = t;
    }
    public void setMainActivity(MainActivity a) { mainActivity = a; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_attractions);
        ImageButton floatingButton = (ImageButton) view.findViewById(R.id.fab);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Kappa", Toast.LENGTH_LONG).show();
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        AttractionParser parser = new AttractionParser(getActivity().getApplicationContext());
        ParseQuery<Attraction> query = parser.generateAttractionQuery("heritage_district");

        query.findInBackground(new FindCallback<Attraction>() {
            @Override
            public void done(List<Attraction> a, ParseException e) {
                attractions = a;

                if(attractions != null) {
                    adapter = new AttractionsAdapter(mainActivity, attractions);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        return view;
    }

    public void switchAttractionType(String type) {
        attractions.clear();
        AttractionParser parser = new AttractionParser(mainActivity.getApplicationContext());
        String cleanedType = "";

        if(type.equals("Heritage District")) {
            cleanedType = "heritage_district";
            toolbar.setTitle("Heritage District");

        } else if(type.equals("Hotels")) {
            cleanedType = "hotels";
            toolbar.setTitle("Hotels");

        } else if(type.equals("Points of Interest")) {
            cleanedType = "points_of_interest";
            toolbar.setTitle("Points of Interest");

        } else if(type.equals("Restaurants")) {
            cleanedType = "restaurants";
            toolbar.setTitle("Restaurants");
        }

        ParseQuery<Attraction> query = parser.generateAttractionQuery(cleanedType);
        query.findInBackground(new FindCallback<Attraction>() {
            @Override
            public void done(List<Attraction> attractions, ParseException e) {
                adapter = new AttractionsAdapter(mainActivity, attractions);
                recyclerView.setAdapter(adapter);
            }
        });
    }
}

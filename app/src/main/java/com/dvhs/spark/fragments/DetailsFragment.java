package com.dvhs.spark.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvhs.spark.activities.MainActivity;
import com.dvhs.spark.R;
import com.dvhs.spark.adapters.CommentsAdapter;
import com.dvhs.spark.models.Attraction;
import com.dvhs.spark.models.Comment;
import com.melnykov.fab.FloatingActionButton;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kaan on 3/12/15.
 */
public class DetailsFragment extends Fragment {
    public Attraction attraction;
    Toolbar toolbar;
    MainActivity mainActivity;
    ListView listViewComments;
    RatingBar ratingBar;
    ReviewDialogFragment reviewDialog = new ReviewDialogFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        FloatingActionButton fabWriteReview = (FloatingActionButton) view.findViewById(R.id.fab_write_review);
        ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);

        TextView textPhone = (TextView) view.findViewById(R.id.text_phone);
        TextView textWebsite = (TextView) view.findViewById(R.id.text_url);

        textPhone.setText(attraction.getPhone() + "");
        textWebsite.setText(attraction.getWebsite() + "");

        final DetailsFragment detailsFragment = this;
        fabWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewDialog.setAttraction(attraction);
                reviewDialog.setDetailsFragment(detailsFragment);
                reviewDialog.show(mainActivity.getFragmentManager(), "ReviewDialogFragment");
            }
        });

        //this.setEnterTransition(new Fade());
        //this.setReenterTransition(new Fade());



        ImageView imageMap = (ImageView) view.findViewById(R.id.image_map);
        Picasso.with(getActivity().getApplicationContext()).
                load(attraction.generateMapURL()).into(imageMap);

        imageMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.co.in/maps?q=" + attraction.getAddress() + " Gilbert AZ";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                getActivity().startActivity(intent);

            }
        });

        toolbar.setTitle(attraction.getName());


        listViewComments = (ListView) view.findViewById(R.id.list_view_comments);
        refreshComments();

        return view;
    }

    public void refreshComments() {
        ParseQuery<Comment> query = new ParseQuery<Comment>("Comment");
        query.whereEqualTo("attraction", attraction);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                CommentsAdapter adapter = new CommentsAdapter(getActivity().getApplicationContext(), comments);
                listViewComments.setAdapter(adapter);

                int totalRating = 0;
                int numRatings = 0;

                for(Comment c: comments) {
                    totalRating += c.getRating();
                    numRatings++;
                }

                ratingBar.setRating((totalRating * 1.0f) / numRatings);
            }
        });
    }

    public void setAttraction(Attraction a) {
        attraction = a;
    }

    public void setToolbar(Toolbar t) {
        toolbar = t;
    }

    public void setMainActivity(MainActivity m) {
        mainActivity = m;
    }

}

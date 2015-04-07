package com.dvhs.spark.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dvhs.spark.models.Attraction;
import com.dvhs.spark.activities.MainActivity;
import com.dvhs.spark.R;
import com.dvhs.spark.models.Comment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kaan on 2/14/15.
 */
public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.AttractionViewHolder> {
    private MainActivity mainActivity;
    private List<Attraction> attractionList;

    public AttractionsAdapter(MainActivity mainActivity, List<Attraction> attractionList) {
        this.mainActivity = mainActivity;
        this.attractionList = attractionList;
    }

    @Override
    public int getItemCount() {
        return attractionList.size();
    }

    @Override
    public void onBindViewHolder(final AttractionViewHolder viewHolder, int i) {
        final Attraction a = attractionList.get(i);
        viewHolder.name.setText(a.getName());
        viewHolder.name.setBackgroundColor(Color.argb(50, 0, 0, 0));
        viewHolder.address.setText(a.getAddress() + ", Gilbert AZ");
        Picasso.with(mainActivity.getApplicationContext()).load(a.generateMapURL()).into(viewHolder.map);
        viewHolder.map.setColorFilter(Color.argb(140, 255, 255, 255));

        ParseQuery<Comment> query = new ParseQuery<Comment>("Comment");
        query.whereEqualTo("attraction", a);

        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                int totalRating = 0;
                int numRatings = 0;

                for (Comment c : comments) {
                    totalRating += c.getRating();
                    numRatings++;
                }
                viewHolder.ratingBar.setRating((totalRating * 1.0f) / numRatings);
            }
        });

        viewHolder.learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.openDetailsView(a);
            }
        });

    }

    @Override
    public AttractionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_attraction, viewGroup, false);

        return new AttractionViewHolder(itemView);
    }

    public static class AttractionViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected TextView address;
        protected Button learnMore;
        protected ImageView map;
        protected RatingBar ratingBar;


        public AttractionViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.text_attraction_name);
            address = (TextView) v.findViewById(R.id.text_attraction_address);
            map = (ImageView) v.findViewById(R.id.image_map);
            learnMore = (Button) v.findViewById(R.id.button_learn_more);
            ratingBar = (RatingBar) v.findViewById(R.id.rating_bar);
        }
    }
}

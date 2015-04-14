package com.dvhs.spark.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dvhs.spark.extras.CircleTransform;
import com.dvhs.spark.models.Comment;
import com.dvhs.spark.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kaan on 3/19/15.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {
    private final Context context;
    private final List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        super(context, R.layout.list_item_comment, comments);
        this.context = context;
        this.comments = comments;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_comment, parent, false);

        TextView textComment = (TextView) rowView.findViewById(R.id.comment);
        TextView textName = (TextView) rowView.findViewById(R.id.name);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.rating);
        ImageView imageProfile = (ImageView) rowView.findViewById(R.id.image_profile);

        if (comments.get(position).getUserProfileImageURL() != null) {
            Picasso.with(context).load(comments.get(position).getUserProfileImageURL())
                    .transform(new CircleTransform()).into(imageProfile);
        }

        textComment.setText(comments.get(position).getText());

        try {
            textName.setText(comments.get(position).getUser().fetchIfNeeded().getString("name") + ": ");
        } catch (com.parse.ParseException e) {

        }

        ratingBar.setRating(comments.get(position).getRating());
        return rowView;
    }
}


package com.dvhs.spark.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dvhs.spark.R;
import com.dvhs.spark.models.Attraction;
import com.dvhs.spark.models.Comment;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by kaan on 3/17/15.
 */
public class ReviewDialogFragment extends DialogFragment {

    Attraction attraction;
    DetailsFragment detailsFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.dialog_fragment_review, null);

        final TextView text = (TextView) v.findViewById(R.id.comment_text);
        final RatingBar rating = (RatingBar) v.findViewById(R.id.comment_rating);

        AlertDialog.Builder builder =  new  AlertDialog.Builder(getActivity())
                .setPositiveButton("Submit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                createComment(text.getText().toString(), Math.round(rating.getRating()));
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );



        builder.setView(v);
        return builder.create();
    }

    private void createComment(String text, int rating) {
        Comment comment = new Comment(text, rating);
        comment.setAttraction(attraction);
        comment.setUser(ParseUser.getCurrentUser());
        comment.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                detailsFragment.refreshComments();
            }
        });
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public void setDetailsFragment(DetailsFragment detailsFragment) {
        this.detailsFragment = detailsFragment;
    }
}

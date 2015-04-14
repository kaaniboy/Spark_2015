package com.dvhs.spark.extras;

import com.dvhs.spark.models.Attraction;

import java.util.Comparator;

/**
 * Created by kaan on 4/13/15.
 */
public class AttractionComparator implements Comparator<Attraction> {
    @Override
    public int compare(Attraction first, Attraction second) {
        float firstRating = 0;
        float secondRating = 0;

        if(first.getRatingCount() != 0) {
            firstRating = 100 * (first.getRatingTotal() * 1.0f / first.getRatingCount());
        }

        if(second.getRatingCount() != 0) {
            secondRating = 100 * (second.getRatingTotal() * 1.0f / second.getRatingCount());
        }

        return (int)(secondRating - firstRating);

    }
}

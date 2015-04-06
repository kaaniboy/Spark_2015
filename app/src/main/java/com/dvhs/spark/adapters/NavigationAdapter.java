package com.dvhs.spark.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvhs.spark.R;

/**
 * Created by kaan on 3/4/15.
 */
public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationItemViewHolder> {

    private Context context;
    private String[] attractionTypes;

    public NavigationAdapter(Context context, String[] attractionTypes) {
        this.context = context;
        this.attractionTypes = attractionTypes;
    }

    @Override
    public int getItemCount() {
        return attractionTypes.length;
    }

    @Override
    public void onBindViewHolder(NavigationItemViewHolder viewHolder, int i) {
        String type = attractionTypes[i];
        viewHolder.name.setText(type);

    }

    @Override
    public NavigationItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_attraction, viewGroup, false);

        return null;
    }

    public static class NavigationItemViewHolder extends RecyclerView.ViewHolder {
        protected TextView name;
        protected ImageView icon;

        public NavigationItemViewHolder(View v) {
            super(v);
            //name =  (TextView) v.findViewById(R.id.text_attraction_type);
            //icon = (ImageView) v.findViewById(R.id.row_icon);
        }
    }
}

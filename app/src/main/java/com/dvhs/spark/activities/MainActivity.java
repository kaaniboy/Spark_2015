package com.dvhs.spark.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dvhs.spark.extras.AttractionParser;
import com.dvhs.spark.R;
import com.dvhs.spark.fragments.BrowseFragment;
import com.dvhs.spark.fragments.DetailsFragment;
import com.dvhs.spark.models.Attraction;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    FragmentManager fragmentManager = getFragmentManager();
    BrowseFragment browseFragment = new BrowseFragment();
    DetailsFragment detailsFragment = new DetailsFragment();
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;
    ListView listViewNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Heritage District");
        setSupportActionBar(toolbar);
        browseFragment.setToolbar(toolbar);
        browseFragment.setMainActivity(this);

        detailsFragment.setToolbar(toolbar);
        detailsFragment.setMainActivity(this);

        listViewNavigation = (ListView) findViewById(R.id.list_view_attraction_types);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragment, browseFragment);
        ft.commit();

        setupNavigationDrawer();


    }

    private void setupNavigationDrawer() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(drawerToggle);

        ArrayAdapter<String> navigationAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.list_item_navigation,
                        getResources().getStringArray(R.array.attraction_types));

        listViewNavigation.setAdapter(navigationAdapter);

        listViewNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String option = ((TextView) view.findViewById(R.id.text_view)).getText().toString();
                browseFragment.switchAttractionType(option);

                if(!browseFragment.isVisible()) {
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.fragment, browseFragment);
                    ft.commit();
                }

                drawer.closeDrawers();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            finish();
        }

    }

    public void openDetailsView(Attraction a) {
        detailsFragment.setAttraction(a);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragment, detailsFragment);
        ft.addToBackStack("BrowseFragment");
        ft.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

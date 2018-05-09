package com.example.pdarb.android_project_showcase;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                                BrowseFragment.OnFragmentInteractionListener,
                                                                HomeFragment.OnFragmentInteractionListener,
                                                                FilterFragment.OnFragmentInteractionListener,
                                                                MapFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;

    private BrowseFragment browseFragment;
    private MapFragment mapFragment;
    private HomeFragment homeFragment;

    public static BottomNavigationView navigation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        browseFragment = new BrowseFragment();
        mapFragment = new MapFragment();
        homeFragment = new HomeFragment();


        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id
                .content_frame, new HomeFragment()).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        for (int i = 0; i < navigation.getMenu().size(); i++) {
            MenuItem menuItem = navigation.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }
        int id = item.getItemId();
        if(id==R.id.navigation_home) {
            mTextMessage.setText(R.string.title_home);
            Log.d("navigation", "home");
            fragment = homeFragment;
        }
        else if(id==R.id.navigation_browse) {
            mTextMessage.setText(R.string.title_browse);
            Log.d("navigation", "browse");
            fragment = browseFragment;
        }
        else if(id==R.id.navigation_map){
            mTextMessage.setText(R.string.title_map);
            Log.d("navigation","map");
            fragment = mapFragment;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id
                .content_frame, fragment).addToBackStack(null).commit();

        //navigation.setSelectedItemId(id);

        return true;
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
*/



    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

}

package com.ecaft.pdarb.android_project_showcase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

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

    public static SQLiteDatabase favorites;



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

        favorites = new DatabaseHelper(getApplicationContext()).getWritableDatabase();

        getSupportFragmentManager().beginTransaction().replace(R.id
                .content_frame, new BrowseFragment()).commit();
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
            //mTextMessage.setText(R.string.title_home);
            fragment = homeFragment;
        }
        else if(id==R.id.navigation_browse) {
            //mTextMessage.setText(R.string.title_browse);
            fragment = browseFragment;
        }
        else if(id==R.id.navigation_map){
            //mTextMessage.setText(R.string.title_map);
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

    public static void deleteRow(String id) {
        favorites.delete(DatabaseSchema.Favorites.NAME, DatabaseSchema.Favorites.Cols.PROJECT_NAME + " = ?",
                new String[]{id});
    }

    public static void addRow(String name){
        ContentValues cv = new ContentValues();
        cv.put(DatabaseSchema.Favorites.Cols.PROJECT_NAME, name);
        cv.put(DatabaseSchema.Favorites.Cols.VISITED, 0);
        favorites.insert(DatabaseSchema.Favorites.NAME, null, cv);
    }

    public static boolean isInFavorites(String name){
        Cursor c = favorites.query(DatabaseSchema.Favorites.NAME, null, null, null, null, null, null);
        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(name.equals(c.getString(c.getColumnIndex(DatabaseSchema.Favorites.Cols.PROJECT_NAME)))){
                    return true;
                }
                c.moveToNext();
            }
        }
        finally {
            c.close();
        }
        return false;
    }

    public static boolean isChecked(String name){
        Cursor c = favorites.query(DatabaseSchema.Favorites.NAME, null, null, null, null, null, null);
        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(name.equals(c.getString(c.getColumnIndex(DatabaseSchema.Favorites.Cols.PROJECT_NAME)))){
                    if(c.getInt(c.getColumnIndex(DatabaseSchema.Favorites.Cols.VISITED))==1){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                c.moveToNext();
            }
        }
        finally {
            c.close();
            return false;
        }
    }

    public static void changeChecked(String name, int newChecked){
        String query = "update " + DatabaseSchema.Favorites.NAME + " set " +
                DatabaseSchema.Favorites.Cols.VISITED + " = " + newChecked + " where " +
                DatabaseSchema.Favorites.Cols.PROJECT_NAME + " = \"" + name + "\"";
        favorites.execSQL(query);

    }

    public static List<String> saved(){
        Cursor c = favorites.query(DatabaseSchema.Favorites.NAME, null, null, null, null, null, null);
        List<String> saved = new ArrayList<String>();
        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                saved.add(c.getString(c.getColumnIndex(DatabaseSchema.Favorites.Cols.PROJECT_NAME)));
                c.moveToNext();
            }
        }
        finally {
            c.close();
            return saved;
        }
    }

    public static List<String> checked(){
        Cursor c = favorites.query(DatabaseSchema.Favorites.NAME, null, null, null, null, null, null);
        List<String> checked = new ArrayList<String>();
        try{
            c.moveToFirst();
            while(!c.isAfterLast()){
                if(c.getInt(c.getColumnIndex(DatabaseSchema.Favorites.Cols.VISITED))==1){
                    checked.add(c.getString(c.getColumnIndex(DatabaseSchema.Favorites.Cols.PROJECT_NAME)));
                }
                c.moveToNext();
            }
        }
        finally {
            c.close();
            return checked;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }


}

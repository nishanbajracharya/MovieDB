package com.nishan.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    static final Movie[] moviesSkeleton = {
            new Movie("", "Logan", "Action, Superhero", "9.3/10", "Long Movie Plot Here", "", "", "", "", "", "", "", ""),
            new Movie("", "", "", "", "", "", "", "", "", "", "", "", ""),
            new Movie("", "", "", "", "", "", "", "", "", "", "", "", "")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ListView movieList = (ListView) findViewById(R.id.movieListItem);

        movieList.setAdapter(new MovieListAdapter(this, moviesSkeleton));
    }
}

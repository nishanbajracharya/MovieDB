package com.nishan.moviedb;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private Movie[] moviesSkeleton = {
            new Movie("", "", "", "", "", "", "", "", "", "", "", "", ""),
            new Movie("", "", "", "", "", "", "", "", "", "", "", "", ""),
            new Movie("", "", "", "", "", "", "", "", "", "", "", "", "")
    };

    static final private Movie[] moviesActual = {
            new Movie("", "Logan", "Action, Superhero", "8.4", "In the near future, a weary Logan cares for an ailing Professor X somewhere on the Mexican border. However, Logan's attempts to hide from the world and his legacy are upended when a young mutant arrives, pursued by dark forces.", "", "", "", "", "", "https://images-na.ssl-images-amazon.com/images/M/MV5BMjI1MjkzMjczMV5BMl5BanBnXkFtZTgwNDk4NjYyMTI@._V1_SX300.jpg", "", ""),

            new Movie("", "Power Rangers", "Action, Superhero", "7.1", "A group of high-school kids, who are infused with unique superpowers, harness their abilities in order to save the world.", "", "", "", "", "", "https://images-na.ssl-images-amazon.com/images/M/MV5BMTU1MTkxNzc5NF5BMl5BanBnXkFtZTgwOTM2Mzk3MTI@._V1_SX300.jpg", "", ""),

            new Movie("", "Ghost in the Shell", "Action, Crime, Drama", "6.8", "In the near future, Major is the first of her kind: A human saved from a terrible crash, who is cyber-enhanced to be a perfect soldier devoted to stopping the world's most dangerous criminals.", "", "", "", "", "", "https://images-na.ssl-images-amazon.com/images/M/MV5BMzJiNTI3MjItMGJiMy00YzA1LTg2MTItZmE1ZmRhOWQ0NGY1XkEyXkFqcGdeQXVyOTk4MTM0NQ@@._V1_SX300.jpg", "", ""),

            new Movie("", "Ghost in the Shell", "Action, Crime, Drama", "6.8", "In the near future, Major is the first of her kind: A human saved from a terrible crash, who is cyber-enhanced to be a perfect soldier devoted to stopping the world's most dangerous criminals.", "", "", "", "", "", "https://images-na.ssl-images-amazon.com/images/M/MV5BMzJiNTI3MjItMGJiMy00YzA1LTg2MTItZmE1ZmRhOWQ0NGY1XkEyXkFqcGdeQXVyOTk4MTM0NQ@@._V1_SX300.jpg", "", ""),

            new Movie("", "Ghost in the Shell", "Action, Crime, Drama", "6.8", "In the near future, Major is the first of her kind: A human saved from a terrible crash, who is cyber-enhanced to be a perfect soldier devoted to stopping the world's most dangerous criminals.", "", "", "", "", "", "https://images-na.ssl-images-amazon.com/images/M/MV5BMzJiNTI3MjItMGJiMy00YzA1LTg2MTItZmE1ZmRhOWQ0NGY1XkEyXkFqcGdeQXVyOTk4MTM0NQ@@._V1_SX300.jpg", "", "")
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.display_name);
        setSupportActionBar(myToolbar);

        final ListView movieList = (ListView) findViewById(R.id.movieListItem);

        final MovieListAdapter movieListAdapter = new MovieListAdapter(this, moviesActual);
        movieList.setAdapter(movieListAdapter);

    }
}

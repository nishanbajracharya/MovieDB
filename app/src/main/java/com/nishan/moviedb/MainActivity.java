package com.nishan.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private ListView movieList;

    final ArrayList<MovieList> movieSkeleton = new ArrayList<MovieList>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/gotham-book.ttf");

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.display_name);

        setSupportActionBar(myToolbar);

        movieList = (ListView) findViewById(R.id.movieListItem);

        for(int i = 0; i < 4; i++) movieSkeleton.add(new MovieList("", "", "", "", ""));

        final MovieListAdapter movieListAdapter = new MovieListAdapter(this, movieSkeleton);
        movieList.setAdapter(movieListAdapter);

        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                MovieList movie = (MovieList) adapter.getItemAtPosition(position);

                Intent i = new Intent(getApplicationContext(), MovieDetailActivity.class);
                i.putExtra("id", movie.getImdbID());
                i.putExtra("title", movie.getTitle());
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        getMovies("a", 1);

    }

    private void getMovies(String searchParam, int page) {
        APIClient apiClient = new APIClient();
        Retrofit retrofit = apiClient.getClient(getBaseContext());

        APIInterface client = retrofit.create(APIInterface.class);

        Call<SearchResponse> call = client.getMovieList(searchParam, page);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                ArrayList<MovieList> movies = new ArrayList(response.body().getSearch());

                final MovieListAdapter movieListAdapter = new MovieListAdapter(getBaseContext(), movies);

                movieList.setAdapter(movieListAdapter);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "error happen ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

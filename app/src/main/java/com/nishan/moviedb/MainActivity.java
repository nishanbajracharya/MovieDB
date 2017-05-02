package com.nishan.moviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.nishan.moviedb.retrofit.APIClient;
import com.nishan.moviedb.retrofit.APIInterface;
import com.nishan.moviedb.utils.TypefaceUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView movieList;

    private final List<MovieList> movieSkeleton = new ArrayList<>();

    private ArrayList<MovieList> movies;

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

        movieList = (RecyclerView) findViewById(R.id.movieListItem);
        movieList.setLayoutManager(new LinearLayoutManager(this));

        for(int i = 0; i < 4; i++) movieSkeleton.add(new MovieList("", "", "", "", ""));

        final MovieListAdapter movieListAdapter = new MovieListAdapter(movieSkeleton, this);
        movieList.setAdapter(movieListAdapter);

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
                movies = new ArrayList<>(response.body().getSearch());

                final MovieListAdapter movieListAdapter = new MovieListAdapter(movies, getBaseContext());

                movieList.setAdapter(movieListAdapter);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, getApplicationContext().getString(R.string.api_fetch_fail), Toast.LENGTH_LONG).show();
            }
        });
    }

}

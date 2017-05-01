package com.nishan.moviedb;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            return;
        }

        String id = extras.getString("id");

        getMovieInfo(id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(extras.getString("title"));
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_back);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getMovieInfo(String id) {
        APIClient apiClient = new APIClient();
        Retrofit retrofit = apiClient.getClient(getApplicationContext());

        APIInterface client = retrofit.create(APIInterface.class);

        Call<Movie> call = client.getMovie(id);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo = response.body();

                ImageView movieDetailBG = (ImageView) findViewById(R.id.movieDetailBG);
                ImageView movieDetailPoster = (ImageView) findViewById(R.id.movieDetailPoster);

                new DownloadImage(movieDetailBG).execute(getHighResPoster(movieInfo.getPoster()));
                new DownloadImage(movieDetailPoster).execute(movieInfo.getPoster());

                TextView movieDetailTitle = (TextView) findViewById(R.id.movieDetailTitle);
                movieDetailTitle.setText(movieInfo.getTitle());
                movieDetailTitle.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

                TextView movieDetailMetaInfo = (TextView) findViewById(R.id.movieDetailMetaInfo);
                movieDetailMetaInfo.setText(movieInfo.getReleased() + " | " + movieInfo.getRuntime());
                movieDetailMetaInfo.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

                TextView movieDetailGenre = (TextView) findViewById(R.id.movieDetailGenre);
                movieDetailGenre.setText(movieInfo.getGenre());
                movieDetailGenre.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));

                RatingBar rating = (RatingBar) findViewById(R.id.movieDetailRating);
                try {
                    rating.setRating(Float.parseFloat(movieInfo.getImdbRating()));
                } catch (Exception e) {
                    rating.setVisibility(View.INVISIBLE);
                    rating.setRating(0);
                }

                TextView movieDetailRatingText = (TextView) findViewById(R.id.movieDetailRatingText);
                movieDetailRatingText.setText(movieInfo.getImdbRating() + "/10");
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.API_MOVIE_FAIL), Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getHighResPoster(String url) {
        return url.replace("SX300", "SX600");
    }
}

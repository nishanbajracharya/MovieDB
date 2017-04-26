package com.nishan.moviedb;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

                new DownloadImage(movieDetailBG).execute(getHighResPoster(movieInfo.getPoster()));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.API_FETCH_FAIL), Toast.LENGTH_LONG).show();
            }
        });

    }

    private String getHighResPoster(String url) {
        return url.replace("SX300", "SX600");
    }
}

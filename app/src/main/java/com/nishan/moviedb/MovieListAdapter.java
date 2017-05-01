package com.nishan.moviedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by nishan on 4/24/17.
 */

class MovieListAdapter extends ArrayAdapter<MovieList> {

    private ArrayList<MovieList> movies;

    private Movie movieInfo;

    public MovieListAdapter(Context context, ArrayList<MovieList> movies) {
        super(context, R.layout.movie_list_item, movies);
        this.movies = movies;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.movie_list_item, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.movieTitle);
        TextView genre = (TextView) rowView.findViewById(R.id.movieGenre);
        RatingBar rating = (RatingBar) rowView.findViewById(R.id.movieRating);
        TextView plot = (TextView) rowView.findViewById(R.id.moviePlot);
        ImageView poster = (ImageView) rowView.findViewById(R.id.moviePoster);

        title.setText(movies.get(position).getTitle());
        if (!title.getText().toString().isEmpty()) {
            title.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
        }

        new DownloadImage(poster).execute(movies.get(position).getPoster());

        getMovieInfo(movies.get(position).getImdbID(), genre, rating, plot);

        return rowView;
    }

    private void getMovieInfo(String id, final TextView genre, final RatingBar rating, final TextView plot) {
        APIClient apiClient = new APIClient();
        Retrofit retrofit = apiClient.getClient(getContext());

        APIInterface client = retrofit.create(APIInterface.class);

        Call<Movie> call = client.getMovie(id);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo = response.body();

                genre.setText(movieInfo.getGenre());
                if (!genre.getText().toString().isEmpty()) {
                    genre.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                }

                try {
                    rating.setRating(Float.parseFloat(movieInfo.getImdbRating()));
                } catch (Exception e) {
                    rating.setVisibility(View.INVISIBLE);
                    rating.setRating(0);
                }
                if (rating.getRating() != 0) {
                    plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                }

                plot.setText(movieInfo.getPlot());
                if (!plot.getText().toString().isEmpty()) {
                    plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getContext(), getContext().getString(R.string.api_fetch_fail), Toast.LENGTH_LONG).show();
            }
        });
    }
}

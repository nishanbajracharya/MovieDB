package com.nishan.moviedb;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.lang.Float.parseFloat;

/**
 * Created by nishan on 4/24/17.
 */

public class MovieListAdapter extends ArrayAdapter<MovieList>{

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
        // genre.setText(movies.get(position).getGenre());
        // if (movies[position].getRating() != "") rating.setRating(parseFloat(movies[position].getRating()));
        // plot.setText(movies[position].getPlot());

        if (title.getText().toString() != "") {
            title.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            // genre.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            // rating.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            // plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));

            // Drawable progress = rating.getProgressDrawable();
            // DrawableCompat.setTint(progress, ContextCompat.getColor(getContext(), R.color.ratings));
        }

        new DownloadImage(poster).execute(movies.get(position).getPoster());

        getMovieInfo(movies.get(position).getImdbID(), genre, rating, plot);
        // genre.setText(movieInfo.getGenre());
        // rating.setRating(parseFloat(movieInfo.getImdbRating()));
        // plot.setText(movieInfo.getPlot());


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
                if (genre.getText().toString() != "") {
                    genre.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                }

                if(movieInfo.getImdbRating() != null) rating.setRating(Float.parseFloat(movieInfo.getImdbRating()));
                if (rating.getRating() != 0) {
                    plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                    Drawable progress = rating.getProgressDrawable();
                    DrawableCompat.setTint(progress, ContextCompat.getColor(getContext(), R.color.ratings));
                }

                plot.setText(movieInfo.getPlot());
                if (plot.getText().toString() != "") {
                    plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getContext(), "error happen ", Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.nishan.moviedb;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by nishan on 4/24/17.
 */

public class MovieListAdapter extends ArrayAdapter<Movie>{

    private Movie[] movies;

    public MovieListAdapter(Context context, Movie[] movies) {
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
        TextView rating = (TextView) rowView.findViewById(R.id.movieRating);
        TextView plot = (TextView) rowView.findViewById(R.id.moviePlot);
        ImageView poster = (ImageView) rowView.findViewById(R.id.moviePoster);

        title.setText(movies[position].getTitle());
        genre.setText(movies[position].getGenre());
        rating.setText(movies[position].getRating());
        plot.setText(movies[position].getPlot());

        return rowView;
    }
}

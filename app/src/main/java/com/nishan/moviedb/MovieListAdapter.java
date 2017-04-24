package com.nishan.moviedb;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import static java.lang.Float.parseFloat;

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
        RatingBar rating = (RatingBar) rowView.findViewById(R.id.movieRating);
        TextView plot = (TextView) rowView.findViewById(R.id.moviePlot);
        ImageView poster = (ImageView) rowView.findViewById(R.id.moviePoster);

        title.setText(movies[position].getTitle());
        genre.setText(movies[position].getGenre());
        if (movies[position].getRating() != "") rating.setRating(parseFloat(movies[position].getRating()));
        plot.setText(movies[position].getPlot());

        if (title.getText().toString() != "") {
            title.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            genre.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            rating.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));
            plot.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.transparent));

            Drawable progress = rating.getProgressDrawable();
            DrawableCompat.setTint(progress, ContextCompat.getColor(getContext(), R.color.ratings));
        }

        new DownloadImage(poster).execute(movies[position].getPoster());

        return rowView;
    }
}

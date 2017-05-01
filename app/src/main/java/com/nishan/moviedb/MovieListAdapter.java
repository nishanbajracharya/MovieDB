package com.nishan.moviedb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static java.security.AccessController.getContext;

/**
 * Created by nishan on 4/24/17.
 */

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ItemHolder> {

    private List<MovieList> movies;

    private Movie movieInfo;

    private LayoutInflater inflater;

    private Context context;

    public MovieListAdapter(List<MovieList> movies, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.movies = movies;
        this.context = context;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        MovieList movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        if (!holder.title.getText().toString().isEmpty()) {
            holder.title.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
        }

        new DownloadImage(holder.poster).execute(movie.getPoster());

        getMovieInfo(movie.getImdbID(), holder.genre, holder.rating, holder.plot);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView genre;
        RatingBar rating;
        TextView plot;
        ImageView poster;

        public ItemHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movieTitle);
            genre = (TextView) itemView.findViewById(R.id.movieGenre);
            rating = (RatingBar) itemView.findViewById(R.id.movieRating);
            plot = (TextView) itemView.findViewById(R.id.moviePlot);
            poster = (ImageView) itemView.findViewById(R.id.moviePoster);
        }
    }

    /* @NonNull
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
    }*/

    private void getMovieInfo(String id, final TextView genre, final RatingBar rating, final TextView plot) {
        APIClient apiClient = new APIClient();
        Retrofit retrofit = apiClient.getClient(context);

        APIInterface client = retrofit.create(APIInterface.class);

        Call<Movie> call = client.getMovie(id);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movieInfo = response.body();

                genre.setText(movieInfo.getGenre());
                if (!genre.getText().toString().isEmpty()) {
                    genre.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                }

                try {
                    rating.setRating(Float.parseFloat(movieInfo.getImdbRating()));
                } catch (Exception e) {
                    rating.setVisibility(View.INVISIBLE);
                    rating.setRating(0);
                }
                if (rating.getRating() != 0) {
                    plot.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                }

                plot.setText(movieInfo.getPlot());
                if (!plot.getText().toString().isEmpty()) {
                    plot.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // Toast.makeText(getContext(), getContext().getString(R.string.api_fetch_fail), Toast.LENGTH_LONG).show();
            }
        });
    }
}

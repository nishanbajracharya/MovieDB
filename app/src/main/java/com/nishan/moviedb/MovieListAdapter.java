package com.nishan.moviedb;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nishan.moviedb.retrofit.APIClient;
import com.nishan.moviedb.retrofit.APIInterface;
import com.nishan.moviedb.utils.DownloadImage;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by nishan on 4/24/17.
 */

class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ItemHolder> {

    private List<MovieList> movies;

    private Movie movieInfo;

    private LayoutInflater inflater;

    private Context context;

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

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

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                MovieList movie = movies.get(position);

                Intent i = new Intent(context, MovieDetailActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id", movie.getImdbID());
                i.putExtra("title", movie.getTitle());
                context.startActivity(i);
            }
        });

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

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView genre;
        RatingBar rating;
        TextView plot;
        ImageView poster;
        ItemClickListener clickListener;

        public ItemHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movieTitle);
            genre = (TextView) itemView.findViewById(R.id.movieGenre);
            rating = (RatingBar) itemView.findViewById(R.id.movieRating);
            plot = (TextView) itemView.findViewById(R.id.moviePlot);
            poster = (ImageView) itemView.findViewById(R.id.moviePoster);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }

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
                Toast.makeText(context, context.getString(R.string.api_fetch_fail), Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.nishan.moviedb.retrofit;

import com.nishan.moviedb.Movie;
import com.nishan.moviedb.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/?type=movie")
    Call<SearchResponse> getMovieList(@Query("s") String param, @Query("page") int page);

    @GET("/")
    Call<Movie> getMovie(@Query("i") String id);
}

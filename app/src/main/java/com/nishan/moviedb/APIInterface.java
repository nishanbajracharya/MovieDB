package com.nishan.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/?y=2017&type=movie")
    Call<SearchResponse> getMovieList(@Query("s") String param, @Query("page") int page);

    @GET("/")
    Call<Movie> getMovie(@Query("i") String id);
}

package com.nishan.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/")
    Call<SearchResponse> getMovieList(@Query("s") String param);

    @GET("/")
    Call<Movie> getMovie(@Query("i") String id);
}

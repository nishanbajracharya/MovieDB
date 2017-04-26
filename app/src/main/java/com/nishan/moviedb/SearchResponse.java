package com.nishan.moviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchResponse {

    @SerializedName("Search")
    public ArrayList<MovieList> Search;

    public void setSearch (ArrayList<MovieList> Search) {
        this.Search = Search;
    }

    public ArrayList<MovieList> getSearch () {
        return Search;
    }

}
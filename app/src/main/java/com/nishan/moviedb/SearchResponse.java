package com.nishan.moviedb;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

class SearchResponse {

    @SerializedName("Search")
    private ArrayList<MovieList> Search;

    public void setSearch (ArrayList<MovieList> Search) {
        this.Search = Search;
    }

    public ArrayList<MovieList> getSearch () {
        return Search;
    }

}

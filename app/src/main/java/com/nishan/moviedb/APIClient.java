package com.nishan.moviedb;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class APIClient {
    private static Retrofit retrofit = null;

    static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                      .baseUrl(context.getString(R.string.api_base_url))
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
        }
        return retrofit;
    }
}

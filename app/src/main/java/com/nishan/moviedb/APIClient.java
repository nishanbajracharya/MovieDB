package com.nishan.moviedb;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                      .baseUrl(context.getString(R.string.API_BASE_URL))
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
        }
        return retrofit;
    }
}

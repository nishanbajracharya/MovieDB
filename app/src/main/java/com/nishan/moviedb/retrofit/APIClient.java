package com.nishan.moviedb.retrofit;

import android.content.Context;

import com.nishan.moviedb.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                      .baseUrl(context.getString(R.string.api_base_url))
                      .addConverterFactory(GsonConverterFactory.create())
                      .build();
        }
        return retrofit;
    }
}

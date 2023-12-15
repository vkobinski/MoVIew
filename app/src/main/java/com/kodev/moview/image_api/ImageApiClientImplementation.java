package com.kodev.moview.image_api;

import com.kodev.moview.api.ApiInterface;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageApiClientImplementation {


    private static ImageApiInterface service = null;

    public static ImageApiInterface getClient() {

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://image.tmdb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(ImageApiInterface.class);
    }
}

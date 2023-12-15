package com.kodev.moview.api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiImplementation {

    private static ApiImplementation instance;

    private ApiImplementation() {
    }

    public static ApiImplementation getInstance() {
        if(instance == null) {
            instance = new ApiImplementation();
        }
        return instance;
    }

    public void listDiscoverMovies(ApiCallbacks callback) {
        ApiInterface apiInterface = ApiClient.getClient();

        Call<MovieApi> call = apiInterface.listDiscoverMovies();


        call.enqueue(new Callback<MovieApi>() {
            @Override
            public void onResponse(Call<MovieApi> call, Response<MovieApi> response) {

                System.out.println(response.code());

                if(response.isSuccessful()) {
                    MovieApi moviesApi = response.body();
                    callback.getMoviesCallBack(moviesApi);
                }

            }

            @Override
            public void onFailure(Call<MovieApi> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}


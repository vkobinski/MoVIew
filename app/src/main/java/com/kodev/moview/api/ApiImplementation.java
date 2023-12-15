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

        Call<List<MovieApi>> call = apiInterface.listDiscoverMovies();


        call.enqueue(new Callback<List<MovieApi>>() {
            @Override
            public void onResponse(Call<List<MovieApi>> call, Response<List<MovieApi>> response) {

                if(response.isSuccessful()) {
                    List<MovieApi> moviesApi = response.body();
                    callback.getMoviesCallBack(moviesApi);
                }

            }

            @Override
            public void onFailure(Call<List<MovieApi>> call, Throwable t) {

            }
        });
    }
}


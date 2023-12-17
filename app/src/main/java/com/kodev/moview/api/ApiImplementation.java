package com.kodev.moview.api;

import com.kodev.moview.ui.RecyclerCallback;
import com.kodev.moview.ui.movie_view.MovieViewCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiImplementation {

    private static ApiImplementation instance;

    private int page = 1;

    private ApiImplementation() {
    }

    public static ApiImplementation getInstance() {
        if(instance == null) {
            instance = new ApiImplementation();
        }
        return instance;
    }

    public void resetPage() {
        this.page = 1;
    }

    public void searchMovies(String query, int page,ApiCallbacks callback) {
        ApiInterface apiInterface = ApiClient.getClient();

        Call<MovieApi> call = apiInterface.searchMovies(query, false, "en-US", page);

        call.enqueue(new Callback<MovieApi>() {
            @Override
            public void onResponse(Call<MovieApi> call, Response<MovieApi> response) {

                if (!response.isSuccessful()) return;
                MovieApi movieApi = response.body();
                callback.getMoviesCallBack(movieApi);

            }

            @Override
            public void onFailure(Call<MovieApi> call, Throwable t) {

            }
        });
    }

    public void getMovieList(List<String> movieIds, RecyclerCallback callback) {

        ApiInterface apiInterface = ApiClient.getClient();


        for(String movieId : movieIds) {
            Call<MovieApi.Movie> call = apiInterface.getMovie(movieId);

            call.enqueue(new Callback<MovieApi.Movie>() {
                @Override
                public void onResponse(Call<MovieApi.Movie> call, Response<MovieApi.Movie> response) {
                    if(!response.isSuccessful()) return;
                    callback.addMovie(response.body());
                }

                @Override
                public void onFailure(Call<MovieApi.Movie> call, Throwable t) {

                }
            });
        }

    }

    public void getMovie(String movieId, MovieViewCallback callback) {

        ApiInterface apiInterface = ApiClient.getClient();

        Call<MovieApi.Movie> call = apiInterface.getMovie(movieId);

        call.enqueue(new Callback<MovieApi.Movie>() {
            @Override
            public void onResponse(Call<MovieApi.Movie> call, Response<MovieApi.Movie> response) {

                System.out.println("CODIGO " + response.code());
                System.out.println("CORPO " + response.body());

                if (!response.isSuccessful()) return;
                callback.setMovie(response.body());
            }

            @Override
            public void onFailure(Call<MovieApi.Movie> call, Throwable t) {

            }
        });
    }

    public void listDiscoverMovies(ApiCallbacks callback) {
        ApiInterface apiInterface = ApiClient.getClient();

        Call<MovieApi> call = apiInterface.listDiscoverMovies(page);
        page++;

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


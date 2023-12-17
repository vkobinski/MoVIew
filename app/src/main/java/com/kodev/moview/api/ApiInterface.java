package com.kodev.moview.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMmE5ZDkzOTdjZjRmZjk4YzdhMTY5OTZkOTRjNWI4MyIsInN1YiI6IjY1N2I4OTVjZWEzOTQ5MDBjNGZmOGZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4ZARg-c4PwbpZTv_enRLiO4yp9We9doxTFYOa8RJ99Y",
    })
    @GET("/3/discover/movie")
    Call<MovieApi> listDiscoverMovies();
    // TODO: make discover use the same page logic as searchMovies

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMmE5ZDkzOTdjZjRmZjk4YzdhMTY5OTZkOTRjNWI4MyIsInN1YiI6IjY1N2I4OTVjZWEzOTQ5MDBjNGZmOGZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4ZARg-c4PwbpZTv_enRLiO4yp9We9doxTFYOa8RJ99Y",
    })
    @GET("/3/search/movie")
    Call<MovieApi> searchMovies(
            @Query("query") String query,
            @Query("include_adult") boolean includeAdult,
            @Query("language") String language,
            @Query("page") int page
    );

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMmE5ZDkzOTdjZjRmZjk4YzdhMTY5OTZkOTRjNWI4MyIsInN1YiI6IjY1N2I4OTVjZWEzOTQ5MDBjNGZmOGZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4ZARg-c4PwbpZTv_enRLiO4yp9We9doxTFYOa8RJ99Y",
    })
   @GET("/3/movie/{movieId}?language=en-US")
    Call<MovieApi.Movie> getMovie(@Path("movieId") String movieId);

}

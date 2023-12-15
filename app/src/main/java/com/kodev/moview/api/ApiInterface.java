package com.kodev.moview.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {

    @Headers({"Accept: application/json", "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMmE5ZDkzOTdjZjRmZjk4YzdhMTY5OTZkOTRjNWI4MyIsInN1YiI6IjY1N2I4OTVjZWEzOTQ5MDBjNGZmOGZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4ZARg-c4PwbpZTv_enRLiO4yp9We9doxTFYOa8RJ99Y"})
    @GET("/3/discover/movie")
    Call<List<MovieApi>> listDiscoverMovies();
}

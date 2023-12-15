package com.kodev.moview.image_api;

import com.kodev.moview.api.MovieApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ImageApiInterface {

    @Headers({
            "Accept: application/json",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhMmE5ZDkzOTdjZjRmZjk4YzdhMTY5OTZkOTRjNWI4MyIsInN1YiI6IjY1N2I4OTVjZWEzOTQ5MDBjNGZmOGZhMiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4ZARg-c4PwbpZTv_enRLiO4yp9We9doxTFYOa8RJ99Y",
    })
    @GET("/t/p/original/{image}")
    Call<ResponseBody> getImage(@Path("image") String imagePath);
}

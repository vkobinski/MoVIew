package com.kodev.moview.ui;

import com.kodev.moview.api.MovieApi;

public interface RecyclerCallback {

    public void setMovieView(String movieId);

    public void addMovie(MovieApi.Movie movie);
}

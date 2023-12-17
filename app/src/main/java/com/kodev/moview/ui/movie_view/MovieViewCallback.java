package com.kodev.moview.ui.movie_view;

import com.kodev.moview.api.MovieApi;

public interface MovieViewCallback {

    public void setMovie(MovieApi.Movie movie);
}

package com.kodev.moview;

import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.ApiInterface;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.models.Movie;

import junit.framework.TestCase;

import java.util.List;

import retrofit2.Call;

public class ApiImplementationTest extends TestCase implements ApiCallbacks {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testListDiscoverMovies() {
        ApiImplementation.getInstance().listDiscoverMovies(this);
    }

    @Override
    public void getMoviesCallBack(List<MovieApi> movies) {
        System.out.println(movies);
        assertEquals(movies.size(), 1);
    }
}
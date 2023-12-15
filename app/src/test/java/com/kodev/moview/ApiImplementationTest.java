package com.kodev.moview;

import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.ApiInterface;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.models.Movie;

import junit.framework.TestCase;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;

public class ApiImplementationTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testListDiscoverMovies() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        ApiImplementation.getInstance().listDiscoverMovies(new ApiCallbacks() {
            @Override
            public void getMoviesCallBack(MovieApi movies) {
                System.out.println(movies);
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }

}
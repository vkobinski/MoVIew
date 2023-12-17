package com.kodev.moview;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.kodev.moview.db.MovieAccessHelper;
import com.kodev.moview.models.Movie;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MovieAccessHelperInstrumentedTest {


    // Classe para testar as funções de acesso a base de dados

    Context context;
    MovieAccessHelper helper;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        helper = MovieAccessHelper.getContext(context);
        helper.deleteAll();
    }

    public Movie generateMovie() {
        Movie movie = new Movie();
        movie.setName("Test");
        movie.setRating(1);
        movie.setSeen(1);
        movie.setWatchlist(0);
        movie.setApiId("test_string");

        return movie;
    }

    @Test
    public void addOneMovie() {

        Movie movie = generateMovie();
        Long idMovie = helper.addMovie(movie);

        List<Movie> movies = helper.getAll();

        assertEquals(1,movies.size());
        assertEquals(idMovie,movies.get(0).getId());
        assertEquals(movies.get(0).getName(), movie.getName());
        assertEquals(movies.get(0).getApiId(), movie.getApiId());
    }

}
package com.kodev.moview.customviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.db.MovieAccessHelper;
import com.kodev.moview.image_api.ImageApiClient;
import com.kodev.moview.image_api.ImageCallback;
import com.kodev.moview.models.Movie;
import com.kodev.moview.ui.dashboard.DashboardFragment;

public class MovieCatalog extends LinearLayout {

    private TextView name, desc;
    private ImageView moviePoster;
    private Stars stars;
    private Bookmark bookmark;
    private String apiId;

    public MovieCatalog(Context context) {
        super(context);
        init(null, 0);
    }

    public MovieCatalog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MovieCatalog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }

    public void setData(MovieApi.Movie movie, ApiCallbacks apiCallbacks) {

        Movie movie1 = MovieAccessHelper.getContext(getContext()).searchMovieByApiId(movie.getId().toString());



        moviePoster = findViewById(R.id.movieImage);
            name = findViewById(R.id.movie_name);
            desc = findViewById(R.id.movie_desc);
            stars = findViewById(R.id.stars);
            bookmark = findViewById(R.id.bookmark);

            name.setText(movie.getOriginalTitle());
            desc.setText(movie.getReleaseDate());

            stars.paint(movie.getVoteAverage());

        if(movie1 != null && movie1.isWatchlist()) {
            System.out.println("HERE");
            bookmark.paint(true);
        }else bookmark.paint(false);


        ImageApiClient.getInstance().getImage(movie.getPosterPath(), new ImageCallback() {
            @Override
            public void downloadImage(Bitmap bitmap) {
                moviePoster.setImageBitmap(bitmap);
            }
        });

        bookmark.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movieDB = new Movie();
                movieDB.setName(movie.getOriginalTitle());
                movieDB.setWatchlist(1);
                movieDB.setSeen(0);
                movieDB.setRating(0);
                movieDB.setApiId(movie.getId().toString());

                MovieAccessHelper.getContext(v.getContext()).addMovie(movieDB);

                apiCallbacks.reDownload();
            }
        });
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater.from(getContext()).inflate(R.layout.movie_catalog, this, true);

    }
}
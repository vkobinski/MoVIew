package com.kodev.moview.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.kodev.moview.R;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.image_api.ImageApiClient;
import com.kodev.moview.image_api.ImageCallback;

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

    public void setData(MovieApi.Movie movie) {

            moviePoster = findViewById(R.id.movieImage);
            name = findViewById(R.id.movie_name);
            desc = findViewById(R.id.movie_desc);
            stars = findViewById(R.id.stars);
            bookmark = findViewById(R.id.bookmark);

            name.setText(movie.getOriginalTitle());
            desc.setText(movie.getReleaseDate());

            stars.paint(movie.getVoteAverage());
            bookmark.paint(true);


        ImageApiClient.getInstance().getImage(movie.getPosterPath(), new ImageCallback() {
            @Override
            public void downloadImage(Bitmap bitmap) {
                moviePoster.setImageBitmap(bitmap);
            }
        });
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater.from(getContext()).inflate(R.layout.movie_catalog, this, true);

    }
}
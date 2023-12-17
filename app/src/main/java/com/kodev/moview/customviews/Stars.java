package com.kodev.moview.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kodev.moview.R;
import com.kodev.moview.ui.movie_view.MovieViewFragment;
import com.kodev.moview.ui.movie_view.MovieViewStarsCallback;

import java.util.ArrayList;
import java.util.List;

public class Stars extends LinearLayout {

    private List<ImageView> estrelas = new ArrayList<>();

    private MovieViewStarsCallback movieViewStarsCallback = null;

    public Stars(Context context) {
        super(context);
        init(null, 0);
    }

    public Stars(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Stars(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }
    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater.from(getContext()).inflate(R.layout.sample_stars, this, true);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.stars, defStyle, 0);

        for(int i = 0; i < 5; i++) {
            int estrelaId = getResources().getIdentifier("estrela" + (i+1), "id", getContext().getPackageName());
            estrelas.add(findViewById(estrelaId));

        }


    }

    public void paint(int quantity) {

        ColorDrawable yellowColor = new ColorDrawable(Color.parseColor("#FFF500"));
        ColorDrawable grayColor = new ColorDrawable(Color.parseColor("#d9d9d9"));

        for (ImageView estrela : estrelas) {
            Drawable starDrawable = estrela.getDrawable().mutate();
            starDrawable.setColorFilter(new PorterDuffColorFilter(grayColor.getColor(), PorterDuff.Mode.SRC_IN));
            estrela.setImageDrawable(starDrawable);
        }

        for (int i = 0; i < quantity; i++) {

            Drawable starDrawable = estrelas.get(i).getDrawable().mutate();

            starDrawable.setColorFilter(new PorterDuffColorFilter(yellowColor.getColor(), PorterDuff.Mode.SRC_IN));
            estrelas.get(i).setImageDrawable(starDrawable);
        }
    }
    public void paintByRating(float voteAverage) {
        int quantity = Math.round((voteAverage / 10) * 5);
        paint(quantity);

    }

    public void setMovieViewStarsCallback(MovieViewStarsCallback callback) {

        this.movieViewStarsCallback = callback;

        for(int i = 0; i <  estrelas.size(); i++) {
            int finalI = i;
            estrelas.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieViewStarsCallback.setMovieStars(finalI + 1);
                }
            });
        }

    }
}
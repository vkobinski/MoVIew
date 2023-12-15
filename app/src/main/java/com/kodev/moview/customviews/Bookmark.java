package com.kodev.moview.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kodev.moview.R;

import java.util.ArrayList;
import java.util.List;

public class Bookmark extends LinearLayout {

    private ImageView bookmark;
    public Bookmark(Context context) {
        super(context);
        init(null, 0);
    }

    public Bookmark(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public Bookmark(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater.from(getContext()).inflate(R.layout.bookmark, this, true);

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.bookmark, defStyle, 0);

        ColorDrawable blueColor = new ColorDrawable(Color.parseColor("#3EBBF3"));
        ColorDrawable grayColor = new ColorDrawable(Color.parseColor("#d9d9d9"));



        bookmark = findViewById(R.id.imagembookmark);
    }

    public void paint(boolean paint) {

        ColorDrawable blueColor = new ColorDrawable(Color.parseColor("#3EBBF3"));

        if(paint) {
            Drawable drawable = bookmark.getDrawable().mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(blueColor.getColor(), PorterDuff.Mode.SRC_IN));
            bookmark.setImageDrawable(drawable);
        }
    }
}
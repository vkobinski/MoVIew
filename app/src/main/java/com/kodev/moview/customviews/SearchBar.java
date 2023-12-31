package com.kodev.moview.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;

import java.security.Key;

public class SearchBar extends LinearLayout {

    private ApiCallbacks apiCallbacks;
    private EditText buscaText;
    private ImageView buscaButton;
    private boolean searching = false;

    private int page = 1;

    public SearchBar(Context context) {
        super(context);
        init(null, 0);
    }

    public SearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, 0);
    }

    public void setApiCallbacks(ApiCallbacks apiCallbacks) {
        this.apiCallbacks = apiCallbacks;
    }

    public EditText getBuscaText() {
        return this.buscaText;
    }

    public void oneMorePage() {
        this.page += 1;
    }

    public int getPage() {
        return this.page;
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater.from(getContext()).inflate(R.layout.search_bar, this, true);
        this.setClipToOutline(true);

        buscaText = findViewById(R.id.buscaText);
        buscaButton = findViewById(R.id.buscaButton);

        buscaText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(KeyEvent.KEYCODE_ENTER == keyCode)  {
                    page = 1;
                    getMovies();
                    return true;
                }
                return false;
            }
        });

        buscaButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                getMovies();
            }
        });
    }

    public boolean isSearching() {
        return this.searching;
    }

    public void getMovies() {
        this.searching = true;
        String query = buscaText.getText().toString();
        ApiImplementation.getInstance().searchMovies(query, page, apiCallbacks);
    }

}
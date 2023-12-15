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
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kodev.moview.R;

import java.util.ArrayList;
import java.util.List;

public class Stars extends LinearLayout {

    private List<ImageView> estrelas = new ArrayList<>();

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
            ImageView estrelaAtual = findViewById(estrelaId);
            Drawable estrelaDrawable = estrelaAtual.getDrawable();
            ColorDrawable grayColor = new ColorDrawable(Color.parseColor("#d9d9d9"));
            estrelaDrawable.setColorFilter(new PorterDuffColorFilter(grayColor.getColor(), PorterDuff.Mode.SRC_IN));
            estrelaAtual.setImageDrawable(estrelaDrawable);
            estrelas.add(estrelaAtual);

        }

        ColorDrawable yellowColor = new ColorDrawable(Color.parseColor("#FFF500"));

        int starQuantity = a.getInt(R.styleable.stars_quantity, 0);

        for(int i = 0; i < starQuantity; i++) {
                Drawable starDrawable = estrelas.get(i).getDrawable();
                starDrawable.setColorFilter(new PorterDuffColorFilter(yellowColor.getColor(), PorterDuff.Mode.SRC_IN));
                estrelas.get(i).setImageDrawable(starDrawable);
        }

    }

}
package com.kodev.moview.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kodev.moview.R;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.customviews.Bookmark;
import com.kodev.moview.customviews.Stars;
import com.kodev.moview.db.MovieAccessHelper;
import com.kodev.moview.image_api.ImageApiClient;
import com.kodev.moview.image_api.ImageCallback;
import com.kodev.moview.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class CatalogRecycler extends RecyclerView.Adapter<CatalogRecycler.ViewHolder> {

    private List<MovieApi.Movie> movies = new ArrayList<>();
    private Context context;

    public CatalogRecycler(Context context) {

        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_catalog, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.stars.paint(movies.get(position).getVoteAverage());

        ImageApiClient.getInstance().getImage(movies.get(position).getPosterPath(), new ImageCallback() {
            @Override
            public void downloadImage(Bitmap bitmap) {
                holder.moviePoster.setImageBitmap(bitmap);
            }
        });

        holder.name.setText(movies.get(position).getOriginalTitle());
        holder.desc.setText(movies.get(position).getReleaseDate());


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieApi.Movie> movies) {

        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, desc;
        private ImageView moviePoster;
        private Stars stars;
        private Bookmark bookmark;
        private String apiId;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movieImage);
            name = itemView.findViewById(R.id.movie_name);
            desc = itemView.findViewById(R.id.movie_desc);
            stars = itemView.findViewById(R.id.stars);
            bookmark = itemView.findViewById(R.id.bookmark);

        }

    }
}

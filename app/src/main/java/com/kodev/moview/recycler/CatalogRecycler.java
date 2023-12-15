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

        Movie movie = MovieAccessHelper.getContext(this.context).searchMovieByApiId(movies.get(position).getId().toString());

        System.out.println(movies.get(position).getId().toString());

        if(movie != null) {
            holder.stars.paint(movie.getRating());
            holder.bookmark.paint(movie.isWatchlist());

        }

        ImageApiClient.getInstance().getImage(movies.get(position).getPosterPath(), new ImageCallback() {
            @Override
            public void downloadImage(Bitmap bitmap) {
                holder.moviePoster.setImageBitmap(bitmap);
            }
        });

        holder.name.setText(movies.get(position).getOriginalTitle());
        holder.desc.setText(movies.get(position).getReleaseDate());


        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MovieApi.Movie movieApi = movies.get(holder.getAdapterPosition());

                Movie movie = new Movie();
                movie.setName(movieApi.getOriginalTitle());
                movie.setWatchlist(1);
                movie.setSeen(0);
                movie.setRating(5);
                movie.setApiId(movieApi.getId().toString());

                MovieAccessHelper.getContext(v.getContext()).addMovie(movie);

                notifyDataSetChanged();
            }
        });

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

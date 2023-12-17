package com.kodev.moview.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.kodev.moview.ui.RecyclerCallback;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CatalogRecycler extends RecyclerView.Adapter<CatalogRecycler.ViewHolder> {

    private List<MovieApi.Movie> movies = new ArrayList<>();
    private Context context;
    private RecyclerCallback recyclerCallback;
    HashMap<String, SoftReference<Bitmap>> imageCache =
            new HashMap<>();

    public CatalogRecycler(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_catalog, parent, false);

        return new ViewHolder(view);
    }

    public void setRecyclerCallback(RecyclerCallback callback) {
        this.recyclerCallback = callback;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ColorDrawable grayColor = new ColorDrawable(Color.parseColor("#d9d9d9"));
        holder.moviePoster.setImageDrawable(grayColor);

        MovieAccessHelper movieHelper = MovieAccessHelper.getContext(context);

        MovieApi.Movie movieList = movies.get(position);
        System.out.println(movieList.getId().toString());
        holder.stars.paintByRating(movieList.getVoteAverage());
        Movie movie1 = movieHelper.searchMovieByApiId(movieList.getId().toString());


        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerCallback.setMovieView(movieList.getId().toString());
            }
        });

        holder.moviePoster.setTag(movieList.getId().toString());

        if(movie1 != null && movie1.isWatchlist()) holder.bookmark.paint(true);
        else                                       holder.bookmark.paint(false);

        SoftReference<Bitmap> bitmapSoftReference = imageCache.get(movieList.getId().toString());

        if(movieList.getPosterPath() != null)  {

        if(bitmapSoftReference == null) {


            ImageApiClient.getInstance().getImage(movieList.getId().toString(), movieList.getPosterPath(), new ImageCallback() {
                @Override
                public void downloadImage(String tag, Bitmap bitmap) {
                    imageCache.put(movieList.getId().toString(), new SoftReference<>(bitmap));

                    if(holder.moviePoster.getTag().equals(tag)) holder.moviePoster.setImageBitmap(bitmap);

                }
            });

        } else {
            Bitmap imageBitmap = bitmapSoftReference.get();
            holder.moviePoster.setImageBitmap(imageBitmap);
        }


        }


        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieAccessHelper movieHelper = MovieAccessHelper.getContext(v.getContext());
                MovieApi.Movie clickMovie = movies.get(holder.getAdapterPosition());
                Movie movieDB = movieHelper.searchMovieByApiId(clickMovie.getId().toString());

                if(movieDB != null) {
                    movieHelper.updateMovieWatchlist(clickMovie.getId().toString(), !movieDB.isWatchlist());
                    notifyDataSetChanged();
                    return;
                }

                movieDB = new Movie();
                movieDB.setName(movieList.getOriginalTitle());
                movieDB.setWatchlist(1);
                movieDB.setSeen(0);
                movieDB.setRating(0);
                movieDB.setApiId(movieList.getId().toString());

                movieHelper.addMovie(movieDB);

                notifyDataSetChanged();
            }
        });

        holder.name.setText(movies.get(position).getOriginalTitle());
        holder.desc.setText(movies.get(position).getReleaseDate());


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieApi.Movie> movies, boolean clean) {

        if(clean) this.movies = movies;
        else this.movies.addAll(movies);

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

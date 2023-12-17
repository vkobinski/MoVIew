package com.kodev.moview.ui.movie_view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.customviews.Bookmark;
import com.kodev.moview.customviews.SearchBar;
import com.kodev.moview.customviews.Stars;
import com.kodev.moview.databinding.FragmentDashboardBinding;
import com.kodev.moview.databinding.FragmentMovieViewBinding;
import com.kodev.moview.db.MovieAccessHelper;
import com.kodev.moview.image_api.ImageApiClient;
import com.kodev.moview.image_api.ImageCallback;
import com.kodev.moview.models.Movie;
import com.kodev.moview.recycler.CatalogRecycler;
import com.kodev.moview.ui.dashboard.DashboardViewModel;

import org.w3c.dom.Text;

public class MovieViewFragment extends Fragment implements MovieViewStarsCallback {

    private FragmentMovieViewBinding binding;
    private Context context;
    private ImageView poster;
    private TextView sinopse, name;

    private Stars stars;
    private Bookmark bookmark;
    private Movie movieDb;
    private MovieAccessHelper db;
    private String movieId;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MovieViewModel movieViewModel =
                new ViewModelProvider(this).get(MovieViewModel.class);

        binding = FragmentMovieViewBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.context = root.getContext();

        movieId = getArguments().getString("movieId");

        db = MovieAccessHelper.getContext(context);
        movieDb = db.searchMovieByApiId(movieId);

        poster = root.findViewById(R.id.movie_view_poster);
        sinopse = root.findViewById(R.id.movie_view_sinopse);
        name = root.findViewById(R.id.movie_view_movie_name);
        stars = root.findViewById(R.id.movie_view_stars);
        bookmark = root.findViewById(R.id.movie_view_bookmark);

        ApiImplementation.getInstance().getMovie(movieId, new MovieViewCallback() {
            @Override
            public void setMovie(MovieApi.Movie movie) {
                setImage(movieId, movie);
                name.setText(movie.getOriginalTitle());
                sinopse.setText(movie.getOverView());
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(movieDb == null) {
                    Movie newMovie = new Movie();
                    newMovie.setWatchlist(1);
                    newMovie.setRating(0);
                    newMovie.setSeen(0);
                    newMovie.setApiId(movieId);
                    newMovie.setName(name.getText().toString());

                    db.addMovie(newMovie);
                    setMovieDb(newMovie);
                }

                System.out.println("here");
                db.updateMovieWatchlist(movieId, !movieDb.isWatchlist());
                setMovieDb(db.searchMovieByApiId(movieId));
                renderMovie();
            }
        });

        stars.setMovieViewStarsCallback(this);

        renderMovie();

        return root;
    }

    public void renderMovie() {
        if(movieDb == null) {
            stars.paint(0);
            bookmark.paint(false);
        } else {
            stars.paint(movieDb.getRating());
            bookmark.paint(movieDb.isWatchlist());
        }
    }

    public void setMovieDb(Movie movie) {
        this.movieDb = movie;
    }

    public void setImage(String movieId, MovieApi.Movie movie) {
        ImageApiClient.getInstance().getImage(movieId, movie.getPosterPath(), new ImageCallback() {
            @Override
            public void downloadImage(String tag, Bitmap bitmap) {
                poster.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void setMovieStars(int quantity) {

        if(movieDb == null) {
            Movie newMovie = new Movie();
            newMovie.setWatchlist(0);
            newMovie.setRating(quantity);
            newMovie.setSeen(1);
            newMovie.setApiId(movieId);
            newMovie.setName(name.getText().toString());

            db.addMovie(newMovie);
            setMovieDb(newMovie);
        } else {
            movieDb.setRating(quantity);
            db.updateMovieRating(movieDb.getApiId(), quantity);
        }

       renderMovie();
    }
}
package com.kodev.moview.ui.notifications;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.kodev.moview.databinding.FragmentNotificationsBinding;
import com.kodev.moview.db.MovieAccessHelper;
import com.kodev.moview.models.Movie;
import com.kodev.moview.recycler.CatalogRecycler;
import com.kodev.moview.ui.RecyclerCallback;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements RecyclerCallback, ApiCallbacks {

    private FragmentNotificationsBinding binding;

    private CatalogRecycler catalogRecycler;
    private Context context;
    private List<MovieApi.Movie> movies;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.context = root.getContext();

        movies = new ArrayList<>();

        RecyclerView recyclerView = root.findViewById(R.id.movie_recycler);
        catalogRecycler = new CatalogRecycler(root.getContext());
        catalogRecycler.setRecyclerCallback(this);
        recyclerView.setAdapter(catalogRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

        List<Movie> allWatchList = MovieAccessHelper.getContext(context).getAllWatchList();

        List<String> movieIds = new ArrayList<>();
        allWatchList.forEach((movie) -> movieIds.add(movie.getApiId()));

        ApiImplementation.getInstance().getMovieList(movieIds, this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void getMoviesCallBack(MovieApi movies) {

    }

    @Override
    public void setMovieView(String movieId) {

    }

    @Override
    public void addMovie(MovieApi.Movie movie) {
        movies.add(movie);
        catalogRecycler.setMovies(movies, true);
    }
}
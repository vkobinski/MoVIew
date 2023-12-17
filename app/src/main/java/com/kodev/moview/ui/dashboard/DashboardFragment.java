package com.kodev.moview.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.customviews.SearchBar;
import com.kodev.moview.databinding.FragmentDashboardBinding;
import com.kodev.moview.recycler.CatalogRecycler;
import com.kodev.moview.ui.RecyclerCallback;

public class DashboardFragment extends Fragment  implements ApiCallbacks, RecyclerCallback {

    private FragmentDashboardBinding binding;
    private CatalogRecycler catalogRecycler;
    private Context context;
    private SearchBar searchBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.context = root.getContext();

        RecyclerView recyclerView = root.findViewById(R.id.movie_recycler);
        catalogRecycler = new CatalogRecycler(root.getContext());
        catalogRecycler.setRecyclerCallback(this);
        recyclerView.setAdapter(catalogRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));


        ApiImplementation.getInstance().listDiscoverMovies(this);

        searchBar = root.findViewById(R.id.searchBar);

        searchBar.setApiCallbacks(new ApiCallbacks() {
            @Override
            public void getMoviesCallBack(MovieApi movies) {
                if(searchBar.getPage() == 1) catalogRecycler.setMovies(movies.getResults(), true);
                else catalogRecycler.setMovies(movies.getResults(), false);
            }

        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if(!recyclerView.canScrollVertically(1)) {
                    if(searchBar.isSearching()) {
                        searchBar.oneMorePage();
                        searchBar.getMovies();
                    }else ApiImplementation.getInstance().listDiscoverMovies(DashboardFragment.this);
                }
            }
        });



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        searchBar.getBuscaText().setText("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        ApiImplementation.getInstance().resetPage();
    }

    @Override
    public void getMoviesCallBack(MovieApi movies) {
        catalogRecycler.setMovies(movies.getResults(), false);
    }

    @Override
    public void setMovieView(String movieId) {
        NavController navController = NavHostFragment.findNavController(this);
        Bundle bundle = new Bundle();
        bundle.putString("movieId", movieId);
        navController.navigate(R.id.action_navigation_dashboard_to_navigation_movie_view, bundle);
    }

    @Override
    public void addMovie(MovieApi.Movie movie) {

    }
}
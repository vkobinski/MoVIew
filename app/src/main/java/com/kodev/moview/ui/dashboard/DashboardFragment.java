package com.kodev.moview.ui.dashboard;

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
import com.kodev.moview.databinding.FragmentDashboardBinding;
import com.kodev.moview.recycler.CatalogRecycler;

public class DashboardFragment extends Fragment  implements ApiCallbacks {

    private FragmentDashboardBinding binding;

    private CatalogRecycler catalogRecycler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = root.findViewById(R.id.movie_recycler);
        catalogRecycler = new CatalogRecycler(root.getContext());
        recyclerView.setAdapter(catalogRecycler);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

        ApiImplementation.getInstance().listDiscoverMovies(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void getMoviesCallBack(MovieApi movie) {
        catalogRecycler.setMovies(movie.getResults());
    }
}
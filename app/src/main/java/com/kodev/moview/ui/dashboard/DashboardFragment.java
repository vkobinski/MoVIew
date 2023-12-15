package com.kodev.moview.ui.dashboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kodev.moview.R;
import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.customviews.MovieCatalog;
import com.kodev.moview.databinding.FragmentDashboardBinding;
import com.kodev.moview.image_api.ImageApiClient;
import com.kodev.moview.image_api.ImageCallback;
import com.kodev.moview.recycler.CatalogRecycler;

import org.apache.http.conn.ConnectTimeoutException;

public class DashboardFragment extends Fragment  implements ApiCallbacks {

    private FragmentDashboardBinding binding;
    private LinearLayout linear;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.context = root.getContext();

        linear = root.findViewById(R.id.linear);

        ApiImplementation.getInstance().listDiscoverMovies(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void getMoviesCallBack(MovieApi movies) {
        for(MovieApi.Movie movie : movies.getResults())  {
            MovieCatalog catalog = new MovieCatalog(this.context);
            catalog.setData(movie);
            linear.addView(catalog);

        }
    }
}
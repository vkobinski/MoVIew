package com.kodev.moview;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.kodev.moview.api.ApiCallbacks;
import com.kodev.moview.api.ApiImplementation;
import com.kodev.moview.api.MovieApi;
import com.kodev.moview.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ApiCallbacks {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ApiImplementation.getInstance().listDiscoverMovies(this);
    }

    @Override
    public void getMoviesCallBack(MovieApi movies) {
        System.out.println(movies.getResults().get(0).getOriginalTitle());
    }
}
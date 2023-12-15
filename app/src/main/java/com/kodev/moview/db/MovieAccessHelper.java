package com.kodev.moview.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.kodev.moview.models.Movie;

import java.util.ArrayList;
import java.util.List;

public final class MovieAccessHelper {

    private MovieDbHelper db;
    private static MovieAccessHelper instance;

    private MovieAccessHelper(Context context) {
        db = new MovieDbHelper(context);
    }

    public static MovieAccessHelper getContext(Context context) {
        if(instance == null) instance = new MovieAccessHelper(context);
        return instance;
    }

    // Função que lê cada coluna da tabela e salva numa lista de Filmes
    public List<Movie> readMovies(Cursor cursor) {
        List<Movie> movies = new ArrayList<>();

        while(cursor.moveToNext()) {
            Movie curMovie = new Movie();
            curMovie.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry._ID)));
            curMovie.setName(cursor.getString(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry.COLUMN_NAME_MOVIE_NAME)));
            curMovie.setRating(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry.COLUMN_NAME_RATING)));
            curMovie.setWatchlist(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry.COLUMN_NAME_WATCHLIST)));
            curMovie.setSeen(cursor.getInt(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry.COLUMN_NAME_SEEN)));
            curMovie.setApiId(cursor.getString(cursor.getColumnIndexOrThrow(MovieSqlite.MovieEntry.COLUMN_NAME_API_ID)));
            movies.add(curMovie);
        }

        return movies;
    }

    // Função que lê todos os filmes da tabela
    public List<Movie> getAll() {

        String[] projection = {
                BaseColumns._ID,
                MovieSqlite.MovieEntry.COLUMN_NAME_MOVIE_NAME,
                MovieSqlite.MovieEntry.COLUMN_NAME_WATCHLIST,
                MovieSqlite.MovieEntry.COLUMN_NAME_RATING,
                MovieSqlite.MovieEntry.COLUMN_NAME_API_ID,
                MovieSqlite.MovieEntry.COLUMN_NAME_SEEN,
        };

        Cursor cursor = db.getReadableDatabase().query(
                MovieSqlite.MovieEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return readMovies(cursor);
    }

    // Função que lê todos os filmes da tabela e remove os que não estão marcados como vistos
    public List<Movie> getAllSeen() {

        List<Movie> movies = getAll();
        movies.removeIf((movie) -> !movie.isSeen());
        return movies;
    }

    // Função que lê todos os filmes da tabela e remove os que não estão marcados como "Para Ver"
    public List<Movie> getAllWatchList() {
        List<Movie> movies = getAll();
        movies.removeIf((movie) -> !movie.isWatchlist());
        return movies;
    }


    // Função que adiciona um filme na tabela
    public long addMovie(Movie mv) {
        SQLiteDatabase database = db.getWritableDatabase();


        Movie movie = searchMovieByApiId(mv.getApiId());

        if(movie != null) return -1;

        ContentValues values = new ContentValues();
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_MOVIE_NAME, mv.getName());
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_RATING, mv.getRating());
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_WATCHLIST, mv.getWatchlist());
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_SEEN, mv.getSeen());
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_API_ID, mv.getApiId());

        long newRowId = database.insert(MovieSqlite.MovieEntry.TABLE_NAME, null, values);

        if(newRowId == -1) Log.e("DATABASE", "Could not add movie to database");

        return newRowId;
    }


    // Função que deleta todos os filmes da tabela
    public void deleteAll() {
        db.getWritableDatabase().delete(MovieSqlite.MovieEntry.TABLE_NAME, null,null);
    }

    // Função que procura os filmes vistos por uma String
    public List<Movie> searchSeenMovies(String query) {

        List<Movie> movies = getAllSeen();

        movies.removeIf((movie) -> {
            String name = movie.getName();

            if(name.toLowerCase().contains(query.toLowerCase())) return false;
            return true;
        });

        return movies;
    }


    // Função que procura os filmes "Para Ver" por uma String
    public List<Movie> searchWatchlistMovies(String query) {

        List<Movie> movies = getAllWatchList();

        movies.removeIf((movie) -> {
            String name = movie.getName();

            if(!movie.isSeen()) return true;

            if(name.toLowerCase().contains(query.toLowerCase())) return false;
            return true;
        });

        return movies;
    }

    // Função que altera o status de um filme para visto ou não visto
    public void updateMovieSeen(Long id, boolean seen) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_SEEN, seen);

        String selection = MovieSqlite.MovieEntry._ID + " = ?";
        String[] selectionArgs = { id.toString() };

        int count = database.update(
                MovieSqlite.MovieEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count != -1) Log.e("DATABASE", "Database update by ID affected more than one Row");
    }

    // Função que altera o status de um filme para "Para Ver" ou não
    public void updateMovieWatchlist(Long id, boolean watchlist) {
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MovieSqlite.MovieEntry.COLUMN_NAME_WATCHLIST, watchlist);

        String selection = MovieSqlite.MovieEntry._ID + " = ?";
        String[] selectionArgs = { id.toString() };

        int count = database.update(
                MovieSqlite.MovieEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if(count != -1) Log.e("DATABASE", "Database update by ID affected more than one Row");
    }

    public Movie searchMovieByApiId(String apiId) {
        List<Movie> movies = getAll();

        for(Movie movie : movies) {
            if(movie.getApiId().contains(apiId)) return movie;
        }

        return null;
    }
}

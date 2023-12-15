package com.kodev.moview.db;

import android.provider.BaseColumns;

public final class MovieSqlite {

    private MovieSqlite() {}

    // Classe que define os campos da tabela

    public static class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_MOVIE_NAME = "name";
        public static final String COLUMN_NAME_API_ID = "api_id";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_SEEN = "seen";
        public static final String COLUMN_NAME_WATCHLIST = "watchlist";
    }

    // Comando SQL para definir os tipos das colunas da tabela
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "  + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY," +
                    MovieEntry.COLUMN_NAME_MOVIE_NAME + " TEXT," +
                    MovieEntry.COLUMN_NAME_API_ID + " TEXT," +
                    MovieEntry.COLUMN_NAME_RATING + " INTEGER," +
                    MovieEntry.COLUMN_NAME_SEEN + " INTEGER," +
                    MovieEntry.COLUMN_NAME_WATCHLIST + " INTEGER)";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
}

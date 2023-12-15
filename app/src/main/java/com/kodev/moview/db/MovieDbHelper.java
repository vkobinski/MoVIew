package com.kodev.moview.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MovieDbHelper extends SQLiteOpenHelper {

    // Classe que gerencia o acesso ao arquivo SQLITE
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBMovie.db";

    public MovieDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Função que executa a query para criar a tabela

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieSqlite.SQL_CREATE_ENTRIES);
    }

    // Função que executa a query para deletar a tabela para adicionar novas colunas
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MovieSqlite.SQL_DELETE_ENTRIES);
        onCreate(db);
    }


}

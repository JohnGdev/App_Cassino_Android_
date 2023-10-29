package com.john.teladecadastro.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Helper extends SQLiteOpenHelper {

    public DB_Helper(@Nullable Context context) {
        super(context, "appLogin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = """
                CREATE TABLE user(email TEXT PRIMARY KEY,
                senha TEXT,
                nome TEXT,
                cpf TEXT ) ;
                """;
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

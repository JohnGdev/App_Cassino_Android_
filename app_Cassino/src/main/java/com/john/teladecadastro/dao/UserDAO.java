package com.john.teladecadastro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.john.teladecadastro.helper.DB_Helper;
import com.john.teladecadastro.model.User;

public class UserDAO {
    private final User user;
    private final DB_Helper db;

    public UserDAO(Context ctx, User user) {
        this.db = new DB_Helper(ctx);
        this.user = user;

    }

    public boolean inserirNovoUsuario() {
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", this.user.getEmail());
        values.put("senha", this.user.getSenha());
        values.put("nome", this.user.getNome());
        values.put("cpf", this.user.getCpf());

        long resultado = dbLite.insert("user", null, values);

        return resultado != -1;


    }


    public boolean verificarEmailESenha() {
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql = "SELECT * FROM user where email = ? AND senha = ?";
        Cursor cursor = dbLite.rawQuery(sql, new String[]{this.user.getEmail(), this.user.getSenha()});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }


    }

    public String getNomeDoUsuario(String email) {
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String nomeUsuario = "";

        String[] columns = {"nome"};
        String selection = "email = ?";
        String[] selectionArgs = {email};

        Cursor cursor = dbLite.query("user", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("nome");
                if (columnIndex != -1) {
                    nomeUsuario = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        }

        return nomeUsuario;
    }
    public boolean apagarUsuario(String email) {
        SQLiteDatabase dbLite = this.db.getWritableDatabase();
        int deletedRows = dbLite.delete("user", "email = ?", new String[]{email});
        return deletedRows > 0;
    }


    public boolean atualizarUsuario(User updatedUser) {
        SQLiteDatabase dbLite = this.db.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nome", updatedUser.getNome());
        values.put("cpf", updatedUser.getCpf());
        values.put("email", updatedUser.getEmail());
        values.put("senha", updatedUser.getSenha());


        int rowsAffected = dbLite.update("user", values, "email = ?", new String[]{updatedUser.getEmail()});

        return rowsAffected > 0;
    }
}




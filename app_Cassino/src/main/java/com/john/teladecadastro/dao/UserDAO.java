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
        this.db = new DB_Helper (ctx);
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


    public boolean verificarEmailESenha(){
        SQLiteDatabase dbLite = this.db.getReadableDatabase();
        String sql ="SELECT * FROM user where email = ? AND senha = ?";
        Cursor cursor = dbLite.rawQuery(sql,new String[]{this.user.getEmail(), this.user.getSenha()});

       if( cursor.getCount() > 0){
           cursor.close();
           return true;
       }else {
           cursor.close();
           return false;
       }



    }


}

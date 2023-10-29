package com.john.teladecadastro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.john.teladecadastro.dao.UserDAO;
import com.john.teladecadastro.model.User;

public class Login extends AppCompatActivity {
    UserDAO uDao;
    private TextView text_cadastro;
    private TextView bt_entrar;
    private TextView edit_email;
    private TextView edit_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IniciarComponentes();
        text_cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });
        bt_entrar.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("email", edit_email.getText().toString());
            editor.apply();
            uDao = new UserDAO(getApplicationContext(),
                    new User(edit_email.getText().toString(),
                            edit_senha.getText().toString()));
            if (uDao.verificarEmailESenha()) {
                Intent intent = new Intent(Login.this, pricipal.class);
                startActivity(intent);
            } else {
                Toast.makeText(Login.this, "Dados incorretos", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void IniciarComponentes() {
        text_cadastro = findViewById(R.id.text_cadastro);
        bt_entrar = findViewById(R.id.bt_entrar);
        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);

    }
}
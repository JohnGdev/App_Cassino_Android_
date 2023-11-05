package com.john.teladecadastro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.UserNotAuthenticatedException;
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

        SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
        String savedEmail = sp.getString("email", "");

        if (!savedEmail.isEmpty()) {
            Intent intent = new Intent(Login.this, pricipal.class);
            startActivity(intent);
            finish();
        }

        text_cadastro.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Cadastro.class);
            startActivity(intent);
        });

        bt_entrar.setOnClickListener(v -> {
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();

            uDao = new UserDAO(getApplicationContext(), new User(email, senha));

            if (uDao.verificarEmailESenha()) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("email", email);

                //recupera o nome durante a verificação do login
                String nome = uDao.getNomeDoUsuario(email);
                editor.putString("nome", nome);
                editor.apply();

                Intent intent = new Intent(Login.this, pricipal.class);
                startActivity(intent);
                finish();
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

package com.john.teladecadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.john.teladecadastro.dao.UserDAO;

public class pricipal extends AppCompatActivity {

    private TextView textNomeUser, textNomeEmail;
    private Button bt_deslogar, bt_apagar, bt_Editar;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pricipal);

        textNomeUser = findViewById(R.id.textNomeUser);
        textNomeEmail = findViewById(R.id.textNomeEmail);
        bt_deslogar = findViewById(R.id.bt_deslogar);
        bt_apagar = findViewById(R.id.bt_apagar);
        bt_Editar = findViewById(R.id.bt_Editar);

        sp = getSharedPreferences("app_Cassino", MODE_PRIVATE);

        String savedNome = sp.getString("nome", "");
        String savedEmail = sp.getString("email", "");

        textNomeUser.setText("Nome: " + savedNome);
        textNomeEmail.setText("Email: " + savedEmail);

        bt_deslogar.setOnClickListener(v -> {
            logoutUser();
        });

        bt_apagar.setOnClickListener(v -> {
            String emailToBeDeleted = sp.getString("email", "");

            UserDAO userDAO = new UserDAO(getApplicationContext(), null); // Passe o objeto User, se necessário

            if (userDAO.apagarUsuario(emailToBeDeleted)) {
                logoutUser();
            } else {
                Toast.makeText(pricipal.this, "Falha ao apagar o usuário", Toast.LENGTH_SHORT).show();
            }
        });

        bt_deslogar.setOnClickListener(v -> {
            logoutUser();
        });

        bt_Editar.setOnClickListener(v -> {
            Intent intent = new Intent(pricipal.this, Editar.class);
            startActivity(intent);
        });
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = sp.edit();

        // Remove os dados do SharedPreferences
        editor.remove("email");
        editor.remove("nome");
        // Adicione outras chaves para remover se necessário

        editor.apply();

        // Após deslogar, redireciona para a tela de login
        Intent intent = new Intent(pricipal.this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
        String savedNome = sp.getString("nome", "");
        String savedEmail = sp.getString("email", "");

        textNomeUser.setText("Nome: " + savedNome);
        textNomeEmail.setText("Email: " + savedEmail);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Atualiza os dados na tela principal antes de destruir a atividade
        SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
        String savedNome = sp.getString("nome", "");
        String savedEmail = sp.getString("email", "");

        textNomeUser.setText("Nome: " + savedNome);
        textNomeEmail.setText("Email: " + savedEmail);
    }
}

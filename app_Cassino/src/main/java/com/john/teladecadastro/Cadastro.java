package com.john.teladecadastro;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.john.teladecadastro.dao.UserDAO;
import com.john.teladecadastro.model.User;

public class Cadastro extends AppCompatActivity {

    UserDAO userDAO;
    private EditText edit_email, edit_senha, edit_nome, edit_CPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_nome = findViewById(R.id.edit_nome);
        edit_CPF = findViewById(R.id.edit_CPF);
        Button bt_cadastro = findViewById(R.id.bt_cadastro);

        bt_cadastro.setOnClickListener(v -> {
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();
            String nome = edit_nome.getText().toString();
            String cpf = edit_CPF.getText().toString();

            // Cria um novo usuário com os dados fornecidos
            User newUser = new User(email, nome, senha, cpf);

            // Inicia o UserDAO com o contexto e o novo usuário
            userDAO = new UserDAO(getApplicationContext(), newUser);

            // Tenta inserir o novo usuário no banco de dados
            if (userDAO.inserirNovoUsuario()) {
                // Salva o nome no SharedPreferences após o cadastro bem-sucedido
                saveNameInSharedPreferences(email, senha, nome, cpf);

                Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Cadastro.this, "Falha ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para salvar o nome no SharedPreferences (fora do método onCreate)
    public void saveNameInSharedPreferences(String email, String senha, String nome, String cpf) {
        SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", email);
        editor.putString("senha", senha);
        editor.putString("nome", nome);
        editor.putString("cpf", cpf);
        editor.apply();
    }
}


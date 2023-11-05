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

public class Editar extends AppCompatActivity {

    private EditText edit_email, edit_senha, edit_nome, edit_CPF;
    private Button bt_salvar;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        edit_email = findViewById(R.id.edit_email);
        edit_senha = findViewById(R.id.edit_senha);
        edit_nome = findViewById(R.id.edit_nome);
        edit_CPF = findViewById(R.id.edit_CPF);
        bt_salvar = findViewById(R.id.bt_salvar);

        SharedPreferences sp = getSharedPreferences("app_Cassino", Context.MODE_PRIVATE);
        String savedNome = sp.getString("nome", "");
        String savedCPF = sp.getString("cpf", "");
        String savedEmail = sp.getString("email", "");
        String savedSenha = sp.getString("senha", "");

        edit_nome.setText(savedNome);
        edit_CPF.setText(savedCPF);
        edit_email.setText(savedEmail);
        edit_senha.setText(savedSenha);

        bt_salvar.setOnClickListener(v -> {
            String nome = edit_nome.getText().toString();
            String cpf = edit_CPF.getText().toString();
            String email = edit_email.getText().toString();
            String senha = edit_senha.getText().toString();

            if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(Editar.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                if (!email.equals(savedEmail)) {
                    Toast.makeText(Editar.this, "Não é possível alterar o e-mail", Toast.LENGTH_SHORT).show();
                    return; // Impede a execução adicional do código
                }

                // Atualize o usuário com os novos dados
                User user = new User(email, nome, senha, cpf);
                userDAO = new UserDAO(getApplicationContext(), user); // Pode ser necessário reinicializar o UserDAO
                if (userDAO.atualizarUsuario(user)) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("nome", nome);
                    editor.putString("cpf", cpf);
                    editor.putString("senha", senha);
                    editor.apply();

                    Toast.makeText(Editar.this, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Editar.this, "Falha ao atualizar o usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}

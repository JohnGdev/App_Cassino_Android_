package com.john.teladecadastro.model;

public class User {
    private final String email;
    private  String nome;
    private final String senha;
    private  String cpf;
    public String getCpf() {
        return cpf;
    }



    public User(String email, String nome, String senha, String cpf) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
    }



    public User(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }



    public String getNome() {
        return nome;
    }



    public String getSenha() {
        return senha;
    }


}

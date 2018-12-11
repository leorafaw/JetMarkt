package com.leowalbrinch.jetmarkt;
//CLASSE DO USUARIO
public class Usuario {
    int codigo;
    String nome;
    String senha;
    int nivel;

    public Usuario(){

    }

    public Usuario(int _codigo, String _nome, String _senha, int _nivel){
        this.codigo = _codigo;
        this.nome = _nome;
        this.senha = _senha;
        this.nivel = _nivel;
    }
    public Usuario(String _nome, String _senha, int _nivel){
        this.nome = _nome;
        this.senha = _senha;
        this.nivel = _nivel;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}

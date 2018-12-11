package com.leowalbrinch.jetmarkt;

/**
 * Created by 20151inf0085 on 25/09/2018.
 */
//CLASSE DO PRODUTO
public class Produto {
    int codigo;
    String desc;
    double preco;

    public Produto(){

    }

    public Produto(int _codigo, String _desc, double _preco){
        this.codigo = _codigo;
        this.desc = _desc;
        this.preco = _preco;

    }

    public Produto(String _desc, double _preco){
        this.desc = _desc;
        this.preco = _preco;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}

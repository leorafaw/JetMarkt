package com.leowalbrinch.jetmarkt;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 20151inf0085 on 25/09/2018.
 */
//CLASSE DO BANCO, ONDE CRIA E TEM TUDO AS PARADA DO CRUD
public class BancoDados extends SQLiteOpenHelper {

    //database configs
    private static final int VERSAO = 1;
    private static final String NOME_BANCO = "bd_produtos";
    //tabela produtos
    private static final String NOME_TABELA = "tb_produtos";
    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_DESC = "descricao";
    private static final String COLUNA_PRECO = "preco";
    //tabela usuarios
    private static final String NOME_TABELA_USU = "tb_usuarios";
    private static final String CODIGO_USUARIO = "codigo";
    private static final String COLUNA_USUARIO = "usuario";
    private static final String COLUNA_SENHA = "senha";
    private static final String COLUNA_NIVEL = "nivel";
    //tabela venda
    private static final String CODIGO_VENDA = "codigo";
    private static final String CODIGO_PRODUTO = "codigo_produto";
    private static final String DESC_PRODUTO = "desc_produto";
    private static final String PRECO_PRODUTO = "preco_produto";
    private static final String QTD_PRODUTO = "quantidade";
    private static final String TOTAL_PROD = "total_produto";

    //cria o banco
    public BancoDados(Context context) {
        super(context, NOME_BANCO, null, VERSAO);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE tb_usuarios(" +
                CODIGO_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_USUARIO + " TEXT, " +
                COLUNA_SENHA + " TEXT, " +
                COLUNA_NIVEL + " NUMERIC)";
        db.execSQL(query);

        String QUERY_COLUNA = "CREATE TABLE tb_produtos(" +
                COLUNA_CODIGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUNA_DESC + " TEXT, " +
                COLUNA_PRECO + " NUMERIC)";
        db.execSQL(QUERY_COLUNA);

        String queryVda = "CREATE TABLE tmpVenda (" +
                CODIGO_VENDA + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                CODIGO_PRODUTO + " INTEGER, " +
                DESC_PRODUTO + " TEXT, " +
                PRECO_PRODUTO + " NUMERIC, " +
                QTD_PRODUTO + " NUMERIC, " +
                TOTAL_PROD + " NUMERIC) ";
        db.execSQL(queryVda);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    public void addUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_USUARIO, usuario.getNome());
        values.put(COLUNA_SENHA, usuario.getSenha());
        values.put(COLUNA_NIVEL, usuario.getNivel());

        db.insert("tb_usuarios", null, values);
        db.close();
    }

    public void addProdutoVda(Produto produto, double quantidade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        double vlrProd = produto.getPreco();
        double totVDA = vlrProd * quantidade;

        values.put(CODIGO_PRODUTO, produto.getCodigo());
        values.put(DESC_PRODUTO, produto.getDesc());
        values.put(PRECO_PRODUTO, produto.getPreco());
        values.put(QTD_PRODUTO, quantidade);
        values.put(TOTAL_PROD, totVDA);

        db.insert("tmpVenda", null, values);
        db.close();
    }

    double totalVenda(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT sum(total_produto) FROM tmpVenda";

        try {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null){
                cursor.moveToFirst();
            }
            double totalVenda = Double.parseDouble(cursor.getString(0));
            return totalVenda;
        }catch (Exception e){

        }
        double totalVenda = 0;
        return totalVenda;

    }

    public void apagaTmpVda(){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "codigo IS NOT NULL";
        db.delete("tmpVenda", where, null);
    }

    Usuario selecionarUsuario(String usuario, String senha){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM tb_usuarios WHERE usuario = ? and senha = ?";
        try {
            Cursor cursor = db.rawQuery(query, new String[] {usuario, senha});

            if (cursor != null){
                cursor.moveToFirst();
            }
            Usuario usuario1 = new Usuario(
                    Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)) );
            return usuario1;
        }catch (Exception e){

        }

        Usuario user = new Usuario("", "", 0);
        return user;

    }


    /*CRUD*/

    void addProduto(Produto produto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_DESC, produto.getDesc());
        values.put(COLUNA_PRECO, produto.getPreco());

        db.insert("tb_produtos", null, values);
        db.close();
    }

    Produto selecionarProduto(int codigo){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("tb_produtos", new String[] {COLUNA_CODIGO, COLUNA_DESC, COLUNA_PRECO},
                COLUNA_CODIGO + " = ?", new String[] {String.valueOf(codigo)},
                null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Produto produto = new Produto(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)));

        return produto;

    }

    public List<Produto> listaTodosProdutos() {

        SQLiteDatabase db = this.getWritableDatabase();

        List<Produto> listaProdutos = new ArrayList<Produto>();

        String query = "SELECT codigo, descricao, preco FROM tb_produtos";



        Cursor c = db.rawQuery(query, null);


        if (c.moveToFirst()){
            do {
                Produto produto = new Produto();
                produto.setCodigo(Integer.parseInt(c.getString(0)));
                produto.setDesc(c.getString(1));
                produto.setPreco(Double.parseDouble(c.getString(2)));

                listaProdutos.add(produto);
            }while (c.moveToNext());
        }



            return listaProdutos;

        }

}

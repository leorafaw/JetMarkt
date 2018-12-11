package com.leowalbrinch.jetmarkt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class produtos extends AppCompatActivity {
    ListView listaProdutos;
    BancoDados db = new BancoDados(this);


    //TELA QUE VISUALIZA PRODUTOS
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        Intent get = getIntent();
        final Bundle bundle = get.getExtras();
        final int nvl = bundle.getInt("nivel");

        //intansica do btao la
        Button btCadastro = (Button) findViewById(R.id.btnCadasto);
        Button btnVoltarToStart = (Button) findViewById(R.id.btnVoltarToStart);
        listaProdutos = (ListView)findViewById(R.id.ListViewProdutos);

        listarProdutos();


        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreCadastro = new Intent(produtos.this, cadastrarProduto.class);
                bundle.putInt("nivel", nvl);
                abreCadastro.putExtras(bundle);
                startActivity(abreCadastro);

            }
        });

        btnVoltarToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltaToStart = new Intent(produtos.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("nivel", nvl);
                voltaToStart.putExtras(bundle1);
                startActivity(voltaToStart);
            }
        });


    }

    public void listarProdutos(){


        List<Produto> produtos = db.listaTodosProdutos();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(produtos.this, android.R.layout.simple_list_item_1, arrayList);

        listaProdutos.setAdapter(adapter);


        for (Produto p : produtos){
            //Log.d("LISTA: ", "\nID: " + p.getCodigo() + " Descrição: " + p.getDesc());
            arrayList.add(p.getCodigo() + " - " + p.getDesc() + " - R$ " + p.getPreco());
            adapter.notifyDataSetChanged();
        }
    }
}

package com.leowalbrinch.jetmarkt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //instancia o botao la
        Button btProdutos  = (Button) findViewById(R.id.btnProdutos);
        Button btVender = (Button) findViewById(R.id.btnVender);

        Intent get = getIntent();
        Bundle bundle = get.getExtras();
        final int nvl = bundle.getInt("nivel");

        if (nvl != 2){
            btProdutos.setEnabled(false);
        }


        //TELA INICIAL, MENU PRINCIPAL, ONDE, DEPOIS DE LOGADO, TU ESCOLHE SE QUER IR PRA PRODUTOS OU VENDA
        //onClick do botao la
        btProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("nivel", nvl);

                Intent it = new Intent(MainActivity.this, produtos.class);
                it.putExtras(bundle);
                startActivity(it);
            }
        });//Fim do onclick do botao

        //final Activity activity = this;
        //OnClick do botao 2 la
        btVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreVenda = new Intent(MainActivity.this, vender.class);
                Bundle bundle = new Bundle();
                bundle.putInt("nivel", nvl);
                abreVenda.putExtras(bundle);
                startActivity(abreVenda);
            }
        });

    }
}

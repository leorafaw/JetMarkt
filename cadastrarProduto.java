package com.leowalbrinch.jetmarkt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
//TELA DE CADASTRO DE PRODUTOS
public class cadastrarProduto extends AppCompatActivity {

    private static final Set<String> TXT_DESCS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("", " ")));
    private static final Set<String> TXT_DESCS1 = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("", " ")));
    private static final Set<String> TXT_DESCS2 = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("", " ")));
    private static final Set<String> TXT_DESCS3 = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("", " ")));
    EditText edtDesc, edtPreco;
    Button btnCadastrar;
    Button btnVoltar;

    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produtos);

        edtDesc = (EditText)findViewById(R.id.edtDescri);
        edtPreco = (EditText)findViewById(R.id.edtPreco);
        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnVoltar = (Button)findViewById(R.id.btnVoltar);

        //ESSA PARADA AQUI EMBAIXO É PRA PASSAR CONTEUDO ENTRE TELAS, QUE NO CASO É O NIVEL DE USUARIO, SE É ADM OU CAIXA
        Intent get = getIntent();
        final Bundle bundle = get.getExtras();
        final int nvl = bundle.getInt("nivel");



        //AQUI SO CLICK PRA CADASTRAR
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDesc = edtDesc.getText().toString();
                String txtPreco = edtPreco.getText().toString();

                txtPreco = txtPreco.replaceAll("(?:[^\\d\\,.])", "");
                txtPreco = txtPreco.replaceAll(",", ".");


                if ((txtDesc.equals("")) || (txtDesc.equals(" ")) || (txtPreco.equals("")) || (txtPreco.equals(" "))){
                    Toast.makeText(cadastrarProduto.this, "VERIFIQUE SE TODOS OS CAMPOS ESTÃO PREENCHIDOS", Toast.LENGTH_LONG).show();
                }else{
                    double dbPreco = Double.parseDouble(txtPreco);
                    db.addProduto(new Produto(txtDesc, dbPreco));
                    Toast.makeText(cadastrarProduto.this, "PRODUTO ADICIONADO COM SUCESSO", Toast.LENGTH_LONG).show();
                    edtDesc.setText("");
                    edtPreco.setText("");
                }

            }
        });
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent voltarParaProdutos = new Intent(cadastrarProduto.this, produtos.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt("nivel",nvl);
                voltarParaProdutos.putExtras(bundle);
                startActivity(voltarParaProdutos);
            }
        });


    }
}

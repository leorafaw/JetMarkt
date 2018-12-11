package com.leowalbrinch.jetmarkt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TotalVenda extends AppCompatActivity {
    TextView textView;
    Button btnCancelaVda, btnFinalizaVda;
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_venda);
        //TELA DO TOTAL DE VENDA
        textView = (TextView)findViewById(R.id.txtTotal);
        btnCancelaVda = (Button)findViewById(R.id.btnCancelarVda);
        btnFinalizaVda = (Button)findViewById(R.id.btnFinalizarVda);

        Intent get = getIntent();
        final Bundle bundle = get.getExtras();
        final int nvl = bundle.getInt("nivel");
        final double total = bundle.getDouble("total");

        final String txtTotal = String.valueOf(total);
        textView.setText(txtTotal);

        btnFinalizaVda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.apagaTmpVda();
                bundle.putInt("nvl", nvl);

                Intent intent = new Intent(TotalVenda.this, MainActivity.class);
                intent.putExtras(bundle);
                Toast.makeText(TotalVenda.this, "VENDA FEITA COM SUCESSO, NO VALOR DE "+txtTotal, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });
        btnCancelaVda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("nvl", nvl);

                Intent intent = new Intent(TotalVenda.this, vender.class);
                intent.putExtras(bundle);
                Toast.makeText(TotalVenda.this, "VENDA NÃO FINALIZADA!", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });



    }
}

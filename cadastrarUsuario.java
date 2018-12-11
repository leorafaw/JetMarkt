package com.leowalbrinch.jetmarkt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//TELA DE CADASTRO DE USUARIO
public class cadastrarUsuario extends AppCompatActivity {

    BancoDados db = new BancoDados(this);
    EditText edtCadUsu, edtCadSenha, edtAdmCod;
    Button btnCadUsu, btnVoltarLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        edtCadUsu = (EditText)findViewById(R.id.edtCadUsu);
        edtCadSenha = (EditText)findViewById(R.id.edtCadSenha);
        edtAdmCod = (EditText)findViewById(R.id.edtAdmCod);
        btnCadUsu = (Button) findViewById(R.id.btnCadUsu);
        btnVoltarLogin = (Button)findViewById(R.id.btnVoltarLogin);

        btnCadUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtUsuario = edtCadUsu.getText().toString();
                String txtSenha = edtCadSenha.getText().toString();
                String txtAdCod = edtAdmCod.getText().toString();

                //CADASTRA O USUARIO E ERA ISSO

                if (txtUsuario.equals("") || txtUsuario.equals(" ") || txtSenha.equals("") || txtSenha.equals(" ")){
                    Toast.makeText(cadastrarUsuario.this, "VERIFIQUE SE TODOS OS CAMPOS ESTÃO PREENCHIDOS", Toast.LENGTH_SHORT).show();
                }else{
                    if (txtAdCod.equals("") || txtAdCod.equals(" ")){
                        db.addUsuario(new Usuario(txtUsuario, txtSenha, 1));
                        edtCadUsu.setText("");
                        edtCadSenha.setText("");
                        edtAdmCod.setText("");
                        Toast.makeText(cadastrarUsuario.this, "Usuario Adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    }else{
                        int itAdmCod = Integer.parseInt(txtAdCod);
                        if (itAdmCod == 4018){
                            db.addUsuario(new Usuario(txtUsuario, txtSenha, 2));
                            edtCadUsu.setText("");
                            edtCadSenha.setText("");
                            edtAdmCod.setText("");
                            Toast.makeText(cadastrarUsuario.this, "Usuario Adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                        }else{
                            db.addUsuario(new Usuario(txtUsuario, txtSenha, 1));
                            edtCadUsu.setText("");
                            edtCadSenha.setText("");
                            edtAdmCod.setText("");
                            Toast.makeText(cadastrarUsuario.this, "Usuario Adicionado com sucesso!", Toast.LENGTH_SHORT).show();

                        }
                    }

                }

            }
        });
        btnVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volarParaLogin = new Intent(cadastrarUsuario.this, login.class);
                startActivity(volarParaLogin);
            }
        });


    }
}

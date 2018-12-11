package com.leowalbrinch.jetmarkt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    Button btnLogar;
    Button btnEntraCadastroUsuario;
    EditText usuario, senha;
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnLogar = (Button) findViewById(R.id.btnEntrar);
        btnEntraCadastroUsuario = (Button) findViewById(R.id.btnEntraCadastroUsuario);
        usuario = (EditText) findViewById(R.id.edtUsuario);
        senha = (EditText) findViewById(R.id.edtSenha);

        Usuario user = new Usuario("adm", "adm",2);
        db.addUsuario(user);
        System.out.println(user.getNome().toString());


        //TELA DE LOGIN

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsuario = usuario.getText().toString();
                String txtSenha = senha.getText().toString();
                System.out.println("aaaa"+txtUsuario);
                System.out.println(txtSenha);


                    Usuario usuarioRetorno = db.selecionarUsuario(txtUsuario,txtSenha);

                    if (txtUsuario.equals("") || txtUsuario.equals(" ") || txtSenha.equals("") || txtSenha.equals(" ")){
                        Toast.makeText(login.this, "Verifique se os campos estão preenchidos!", Toast.LENGTH_LONG).show();
                    }else{
                        if (usuarioRetorno.getNome().equals(txtUsuario) || usuarioRetorno.getSenha().equals(txtSenha)){
                            Bundle bundle = new Bundle();
                            int nvl = usuarioRetorno.getNivel();
                            bundle.putInt("nivel", nvl);
                            Intent i = new Intent(login.this, MainActivity.class);
                            i.putExtras(bundle);
                            startActivity(i);
                        }else{
                            Toast.makeText(login.this, "USUARIO INVALIDO!", Toast.LENGTH_LONG).show();
                        }
                    }


            }
        });

        btnEntraCadastroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, cadastrarUsuario.class);
                startActivity(intent);
            }
        });


        //Usuario usuarioRetorno = db.selecionarUsuario("gabriel","pinto");
        //Toast.makeText(this, usuarioRetorno.getSenha(), Toast.LENGTH_LONG).show();
        //db.addUsuario(new Usuario("leonardo", "pinto", 2));
        //Toast.makeText(this, "adicionou se pa", Toast.LENGTH_LONG).show();
    }
}

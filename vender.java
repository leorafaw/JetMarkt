package com.leowalbrinch.jetmarkt;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.io.IOException;

public class vender extends AppCompatActivity {
    //TELA QUE TEM O LEITOR
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;

    BancoDados db = new BancoDados(this);

    public void geraAlerta(final Produto produto, final CameraSource camera, Bundle bundle) {
        AlertDialog.Builder alert = new AlertDialog.Builder(vender.this);
        alert.setTitle(produto.getDesc() + " - R$ " + produto.getPreco());
        alert.setMessage("Quantas unidades deseja adicionar?");

        //NESSE VOID GERA ALERTA TEM UM ALERTDIALOG QUE É A PARADA QUE APARECE QUANDO TU LE UM CODIGO

        //editText
        final EditText ViewQuantidade = new EditText(this);
        ViewQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER);

        alert.setView(ViewQuantidade);
        alert.setPositiveButton("ADICIONAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    final double dbQTD = Double.parseDouble(ViewQuantidade.getText().toString());
                    db.addProdutoVda(produto, dbQTD);
                }catch (Exception e){
                    Toast.makeText(vender.this, "NAO PODE ADICIONAR ZERO À VENDA", Toast.LENGTH_SHORT).show();
                }
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        alert.show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        surfaceView = (SurfaceView) findViewById(R.id.cameraPreview);
        textView = (TextView) findViewById(R.id.txtMSGVDA);
        Button btnVoltar = (Button) findViewById(R.id.btnVoltaParaInicio);
        Button btnTotalizador = (Button)findViewById(R.id.btnCarrinho);

        Intent get = getIntent();
        final Bundle bundle = get.getExtras();
        final int nvl = bundle.getInt("nivel");

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("nivel", nvl);
                Intent itent = new Intent(vender.this, MainActivity.class);
                itent.putExtras(bundle);
                startActivity(itent);

            }
        });
        btnTotalizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bundle.putInt("nivel", nvl);
                double totVda = db.totalVenda();
                bundle.putDouble("total", totVda);

                Intent intent = new Intent(vender.this, TotalVenda.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();


        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                try {
                    //inicia a camera
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            //BRINCA COM AS PARADA QUANDO ACHA UM QR CODE, VIBRA E CRIA A STRING LIDO QUE É O QUE LEU
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            String lido = qrCodes.valueAt(0).displayValue;
                            int itLido = Integer.parseInt(lido);
                            try{
                                Produto produtoRetorno = db.selecionarProduto(itLido);
                                cameraSource.stop();
                                geraAlerta(produtoRetorno, cameraSource, bundle);

                            }catch (Exception e){
                                Toast.makeText(vender.this, "PRODUTO NÃO ENCONTRADO!", Toast.LENGTH_SHORT).show();
                            }




                        }
                    });
                }
            }
        });



    }
}

package com.example.dan.qrcodeexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button btnScan;
    private Button btnGenerate;
    private FrameLayout containerFl;
    private TextView tvQRResult;
    private final Activity activity = this;

    private OnActivityResultDataChanged mOnActivityResultDataChanged;  //interface instance

    //the interface
    public interface OnActivityResultDataChanged{
        void onDataReceived(String data);
    }
    //setListener
    public void setOnActivityResultDataChanged(OnActivityResultDataChanged listener) {
        this.mOnActivityResultDataChanged = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnGenerate = (Button) findViewById(R.id.btn_generate);
        containerFl = (FrameLayout) findViewById(R.id.container);
        tvQRResult = (TextView) findViewById(R.id.tv_result);

        //event listener
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(activity,QRCodeScanner.class);
//                startActivity(i);

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan a QR Code");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,QRCodeGenerator.class);
                startActivity(i);
            }
        });

        //fragment
        QRScanFromFragment frag = new QRScanFromFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container,frag).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode, data);

        if (result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "QR Code Scan Cancelled",Toast.LENGTH_SHORT).show();
            }{
                Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                tvQRResult.setText(result.getContents());
                mOnActivityResultDataChanged.onDataReceived(result.getContents());
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

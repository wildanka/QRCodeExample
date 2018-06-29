package com.example.dan.qrcodeexample;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCodeGenerator extends AppCompatActivity {
    EditText etQRSource;
    ImageView ivQRCode;
    Button btnGenerateQR;
    private String sourceText;
    private String sourceText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        etQRSource = (EditText) findViewById(R.id.et_qr_source);
        ivQRCode = (ImageView) findViewById(R.id.iv_qrcode);
        btnGenerateQR = (Button) findViewById(R.id.btn_generateQR);
        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceText = etQRSource.getText().toString().trim(); //trim will remove the unused whitespace in front and in back
                Log.d("tes",sourceText);
                Log.d("tes",sourceText2);
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(sourceText, BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    ivQRCode.setImageBitmap(bitmap);
                }catch(WriterException e){
                    e.printStackTrace();
                }
            }
        });
    }
}

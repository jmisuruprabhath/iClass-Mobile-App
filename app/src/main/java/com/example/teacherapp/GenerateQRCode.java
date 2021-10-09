package com.example.teacherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class GenerateQRCode extends AppCompatActivity {

    public final static int QRCodeWidth = 500;
    Bitmap bitmap;
    private EditText qrgentext;
    private Button qrgenbtn;
    private Button qrgendownload;
    private ImageView qrgenimage;
    ImageView myImage1, genback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        qrgentext = findViewById(R.id.qrgentext);
        qrgendownload = findViewById(R.id.qrgendownload);
        qrgendownload.setVisibility(View.INVISIBLE);
        qrgenbtn = findViewById(R.id.qrgenbtn);
        myImage1 = findViewById(R.id.myImage1);
        genback = findViewById(R.id.genback);

        myImage1.setAlpha(0.5f);

        genback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GenerateQRCode.this, QRActivity.class);
                startActivity(intent);
            }
        });


        qrgenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrgentext.getText().toString().trim().length()==0) {
                    Toast.makeText(GenerateQRCode.this, "Text entering is required", Toast.LENGTH_SHORT).show();
                }
                else {
                    try{
                        bitmap = textToImageEncode(qrgentext.getText().toString());
                        qrgendownload.setVisibility(View.VISIBLE);
                        qrgendownload.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "code_scanner", null);
                                Toast.makeText(GenerateQRCode.this, "Successfully Downloaded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch(WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private Bitmap textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try{
            bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth, null);
        } catch (IllegalArgumentException e) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for(int y=0; y<bitMatrixHeight; y++) {
            int offSet = y*bitMatrixWidth;
            for(int x=0; x<bitMatrixWidth; x++) {
                pixels[offSet + x] = bitMatrix.get(x,y)? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
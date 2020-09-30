package com.stone.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.stone.pdfreader.dto.PdfDto;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity {

    private final int permissionRequestCode=101;
    PDFManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        manager=new PDFManager();
       checkPermission();
    }
    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},permissionRequestCode);

        }else {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            loadPDF();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void loadPDF() {
        new AsyncTask<Void, Void, ArrayList<PdfDto>>() {

            @Override
            protected ArrayList<PdfDto> doInBackground(Void... voids) {
                return manager.getPlayList();
            }

            @Override
            protected void onPostExecute(ArrayList<PdfDto> pdfDtos) {
                super.onPostExecute(pdfDtos);
                startActivity(MainActivity.goToMainActivity(SplashActivity.this,pdfDtos));
                finish();
            }
        }.execute();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==permissionRequestCode){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadPDF();
            }else {

                checkPermission();
            }
        }

    }
}
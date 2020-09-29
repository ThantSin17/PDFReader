package com.stone.pdfreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.stone.pdfreader.adapter.PDFAdapter;
import com.stone.pdfreader.databinding.ActivityMainBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.itemOnClickListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements itemOnClickListener {
    ActivityMainBinding binding;

    PDFAdapter adapter;
    PDFManager manager;
    static List<PdfDto> pdfList = new ArrayList<>();
    public static Intent goToMainActivity(Context context,ArrayList<PdfDto> list){
        Intent intent=new Intent(context,MainActivity.class);
        pdfList=list;
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        //recyclerView=binding.recyclerView;
        adapter = new PDFAdapter(this,this);
        manager=new PDFManager();
        adapter.setPdfList(pdfList);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);


    }



    @Override
    public void itemOnClick(PdfDto item) {
        startActivity(PDFViewerActivity.gotoViewerActivity(this,item));
    }
}
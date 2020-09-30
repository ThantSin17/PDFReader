package com.stone.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.stone.appupdater.SampleUpdater;
import com.stone.pdfreader.adapter.PDFAdapter;
import com.stone.pdfreader.alertDialog.AboutAlertDialog;
import com.stone.pdfreader.databinding.ActivityMainBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.itemOnClickListener;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements itemOnClickListener {
    ActivityMainBinding binding;

    PDFAdapter adapter;
    PDFManager manager;
    SampleUpdater updater;
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
        updater=new SampleUpdater(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable=getResources().getDrawable(R.drawable.ic_search_black_24dp);
        drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        menu.findItem(R.id.app_bar_search).setIcon(drawable);
        SearchView searchView= (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setImeOptions(6);
        searchView.setIconified(false);
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.filterData(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filterData(s);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.checkUpdate:
                updater.check("https://raw.githubusercontent.com/ThantSin17/PDFReader/master/update.json");
                break;
            case R.id.about:
                new AboutAlertDialog(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemOnClick(PdfDto item) {
        startActivity(PDFViewerActivity.gotoViewerActivity(this,item));
    }
}
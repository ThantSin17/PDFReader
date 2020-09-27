package com.stone.pdfreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;

import com.stone.pdfreader.adapter.PDFAdapter;
import com.stone.pdfreader.databinding.ActivityMainBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.itemOnClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements itemOnClickListener {
    ActivityMainBinding binding;
    RecyclerView recyclerView;
    PDFAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        //recyclerView=binding.recyclerView;
        adapter = new PDFAdapter(this,this);
       // walkdir(new File(Environment.getExternalStorageDirectory().getAbsolutePath()));
        init();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);


    }

    public void init() {
        String selection = "_data LIKE '%.pdf'";
        List<PdfDto> pdfList = new ArrayList<>();
        try (Cursor fileCursor = getApplicationContext().getContentResolver().query(MediaStore.Files.getContentUri("external"), null, selection, null, "_id DESC")) {
            if (fileCursor == null || fileCursor.getCount() <= 0 || !fileCursor.moveToFirst()) {
                // this means error, or simply no results found
                return;
            }
            do {
                // your logic goes here
//
                int pathString= fileCursor.getColumnIndex(MediaStore.Files.FileColumns.DATA);
             //   int uri=fileCursor.getColumnIndex(MediaStore.Files.getContentUri())
                int nameIndex=fileCursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
                long sizeIndex=fileCursor.getColumnIndex(OpenableColumns.SIZE);
                String path = fileCursor.getString(pathString);
                String name=fileCursor.getString(nameIndex);
                //String size=formatSize(sizeIndex);
                long size=fileCursor.getLong((int) sizeIndex);
//                ManageFIleInfoForRecycler temp=new ManageFIleInfoForRecycler();
//                temp.filePath=path;
//                temp.fileName=name;
//                fileInfo.add(temp);
                PdfDto item = new PdfDto();
                item.setTitle(name);
                //item.setSize(formatSize(size));
                item.setUrl(path);
                item.setSize(path);
                pdfList.add(item);
            } while (fileCursor.moveToNext());
        }
        adapter.setPdfList(pdfList);
    }
    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = " Bytes";
            size /= 1024;
            if (size >= 1024) {
                suffix = " MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }
    public void walkdir(File dir) {
        List<PdfDto> pdfList = new ArrayList<>();
        // String pdfPattern = ".pdf";

        File listFile[] = dir.listFiles();

        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".pdf")) {
                        //Do what ever u want

                        if (!listFile[i].getName().equals(" ")) {
                            PdfDto item = new PdfDto();
                            item.setTitle(listFile[i].getName());
                            item.setSize(listFile[i].getAbsolutePath());
                            pdfList.add(item);
                        }
                        // item.setSize(String.valueOf(listFile[i].getTotalSpace()));

                    }
                }

            }

        }
        adapter.setPdfList(pdfList);
    }

    @Override
    public void itemOnClick(PdfDto item) {
        startActivity(PDFViewerActivity.gotoViewerActivity(this,item));
    }
}
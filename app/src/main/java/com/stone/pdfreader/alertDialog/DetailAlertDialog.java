package com.stone.pdfreader.alertDialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.stone.pdfreader.databinding.DetailFileLayoutBinding;
import com.stone.pdfreader.dto.PdfDto;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;

public class DetailAlertDialog {
    public DetailAlertDialog(Context context, PdfDto item) {
        File file=new File(item.getUrl());
        AlertDialog.Builder builder=new AlertDialog.Builder(context);

        DetailFileLayoutBinding binding=DetailFileLayoutBinding.inflate(LayoutInflater.from(context));
        builder.setView(binding.getRoot());
        final AlertDialog dialog=builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        binding.detailFileName.setText(item.getTitle());
        binding.detailFilePath.setText(item.getUrl());
        binding.detailFileSize.setText(formatSize(file.length()));
        binding.detailFileModified.setText(getDate(file.lastModified()));
        binding.detailOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private String getDate(long dir){
        SimpleDateFormat format=new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss");
        Date newDate=new Date(dir);
        return format.format(newDate);
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
}

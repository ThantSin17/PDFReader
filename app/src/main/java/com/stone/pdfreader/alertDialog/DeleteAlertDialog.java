package com.stone.pdfreader.alertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.OnDeleteItemListener;

import java.io.File;

import androidx.appcompat.app.AlertDialog;

public class DeleteAlertDialog {



    public DeleteAlertDialog(final Context context, final PdfDto item, final int position, final OnDeleteItemListener listener) {

        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete PDF File");
        builder.setMessage("Do you want to delete "+item.getTitle()+" !!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File delete=new File(item.getSize());
                if (delete.exists()){
                    if (delete.delete()){
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
                }
                dialogInterface.dismiss();
                listener.deleteItemListener(item,position);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}

package com.stone.pdfreader.alertDialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.stone.pdfreader.PDFManager;
import com.stone.pdfreader.adapter.PDFAdapter;
import com.stone.pdfreader.databinding.RenameLayoutBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.renameItemListener;

import java.io.File;
import java.nio.file.Files;

import androidx.appcompat.app.AlertDialog;

public class RenameAlertDialog {

    private RenameLayoutBinding binding;
    private AlertDialog ad;
    private PDFManager manager;
   private renameItemListener listener;

    public RenameAlertDialog(final Context context, final PdfDto item, final int position, final renameItemListener listener) {
        binding=RenameLayoutBinding.inflate(LayoutInflater.from(context));
        this.listener=listener;

        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(binding.getRoot());

        binding.renameFilename.setText(item.getTitle());
        binding.renameOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.renameEdit.getText().toString().isEmpty() || binding.renameEdit.getText().toString().equals(" ")){
                    Toast.makeText(context, "Please Enter new file name", Toast.LENGTH_SHORT).show();
                }else {

                    File dir=new File(item.getSize());
                    //Toast.makeText(context, dir.getParent(), Toast.LENGTH_SHORT).show();
                    if (dir.exists()){
                        File oldName=new File(dir.getPath());

                        File newName=new File(dir.getParent(),binding.renameEdit.getText().toString()+".pdf");


                                boolean success=oldName.renameTo(newName);
                                if (success){
                                    item.setTitle(newName.getName());
                                    item.setSize(newName.getPath());
                                    Toast.makeText(context, item.getSize(), Toast.LENGTH_SHORT).show();
                                    listener.onRenameItem(item,position);

                                }else {
                                    Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
                                }



                    }

                    ad.dismiss();
                }
            }
        });
        binding.renameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ad.dismiss();
            }
        });
        ad=builder.show();

    }

}

package com.stone.pdfreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.stone.pdfreader.R;
import com.stone.pdfreader.alertDialog.DeleteAlertDialog;
import com.stone.pdfreader.alertDialog.DetailAlertDialog;
import com.stone.pdfreader.alertDialog.RenameAlertDialog;
import com.stone.pdfreader.appUtils.AppUtil;
import com.stone.pdfreader.databinding.ItemLayoutBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.OnDeleteItemListener;
import com.stone.pdfreader.listener.itemOnClickListener;
import com.stone.pdfreader.listener.renameItemListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> implements renameItemListener, OnDeleteItemListener {
    List<PdfDto> pdfList;
    List<PdfDto> itemList;
    Context context;
    itemOnClickListener listener;



    public PDFAdapter(Context context, itemOnClickListener listener) {
        pdfList=new ArrayList<>();
        itemList=new ArrayList<>();
        this.context = context;
        this.listener=listener;
    }

    public void setPdfList(List<PdfDto> pdfList) {
        this.pdfList = pdfList;
        this.itemList=pdfList;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }
    public void filterData(String str){
        if (str.isEmpty() || str.equals(" ")){
            pdfList=itemList;
        }else {
            List<PdfDto> temp=new ArrayList<>();
            for(PdfDto item : itemList){
                if (item.getTitle().contains(str)){
                    temp.add(item);
                }
            }
            pdfList=temp;
        }
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, final int position) {
        final PdfDto item=pdfList.get(position);
        holder.binding.itemTitle.setText(item.getTitle());
        holder.binding.itemSize.setText(AppUtil.getFileSize(item.getSize()));
        holder.binding.itemDate.setText(AppUtil.getFileDate(item.getUrl()));
        holder.binding.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemOnClick(item);
            }
        });
        final PopupMenu popupMenu=new PopupMenu(context,holder.binding.itemPopImage);
        popupMenu.inflate(R.menu.pop_up_menu);
        holder.binding.itemPopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.show();
            }
        });
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.pop_detail:
                        detailFile(position);
                        break;
                    case R.id.pop_rename:
                        renameFile(position);
                        break;
                    case R.id.pop_delete:
                        deleteFile(position);
                        break;
                    case R.id.pop_share:
                        shareFile(position);
                        break;
                    default: return false;
                }
                return true;
            }
        });
        
        //holder.binding.itemPopImage.setOnTouchListener(popupMenu.getDragToOpenListener());
    }




    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private void shareFile(int position) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, new File(pdfList.get(position).getUrl()));
        //Uri attachment = FileProvider.getUriForFile(context, , new File(pdfList.get(position).getUrl()));
        //shareIntent.putExtra(Intent.EXTRA_STREAM, attachment);
        shareIntent.setType("application/pdf");
        context.startActivity(Intent.createChooser(shareIntent,pdfList.get(position).getTitle()));
    }
    private void detailFile(int position) {
        new DetailAlertDialog(context,pdfList.get(position));
    }
    private void renameFile(int position){

        new RenameAlertDialog(context,pdfList.get(position),position,this);

    }
    private void deleteFile(int position) {
        new DeleteAlertDialog(context,pdfList.get(position),position,this);
    }

    @Override
    public void onRenameItem(PdfDto item,int position) {
        pdfList.set(position,item);
        notifyItemChanged(position);

    }

    @Override
    public void deleteItemListener(PdfDto item, int position) {
        pdfList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,pdfList.size());
    }

    public static class PDFViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding binding;
        public PDFViewHolder(@NonNull ItemLayoutBinding view) {
            super(view.getRoot());
            this.binding=view;

        }


    }
}

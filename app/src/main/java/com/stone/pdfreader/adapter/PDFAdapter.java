package com.stone.pdfreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.stone.pdfreader.R;
import com.stone.pdfreader.alertDialog.RenameAlertDialog;
import com.stone.pdfreader.databinding.ItemLayoutBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.itemOnClickListener;
import com.stone.pdfreader.listener.renameItemListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> implements renameItemListener {
    List<PdfDto> pdfList;
    Context context;
    itemOnClickListener listener;



    public PDFAdapter(Context context, itemOnClickListener listener) {
        pdfList=new ArrayList<>();
        this.context = context;
        this.listener=listener;
    }

    public void setPdfList(List<PdfDto> pdfList) {
        this.pdfList = pdfList;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PDFViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, final int position) {
        final PdfDto item=pdfList.get(position);
        holder.binding.itemTitle.setText(item.getTitle());
        holder.binding.itemSize.setText(item.getSize());
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
                        break;
                    case R.id.pop_rename:
                        renameFile(position);
                        break;
                    case R.id.pop_delete:
                        break;
                    case R.id.pop_share:
                        break;
                    default: return false;
                }
                return true;
            }
        });
        
        holder.binding.itemPopImage.setOnTouchListener(popupMenu.getDragToOpenListener());
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
    private void renameFile(int position){

        new RenameAlertDialog(context,pdfList.get(position),position,this);

    }

    @Override
    public void onRenameItem(PdfDto item,int position) {
        pdfList.set(position,item);
        notifyItemChanged(position);

    }

    public static class PDFViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding binding;
        public PDFViewHolder(@NonNull ItemLayoutBinding view) {
            super(view.getRoot());
            this.binding=view;

        }


    }
}

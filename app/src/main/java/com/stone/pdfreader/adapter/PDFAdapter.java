package com.stone.pdfreader.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.pdfreader.databinding.ItemLayoutBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.listener.itemOnClickListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder>{
    List<PdfDto> pdfList;
    Context context;
    itemOnClickListener listener;

    public PDFAdapter(Context context,itemOnClickListener listener) {
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
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        final PdfDto item=pdfList.get(position);
        holder.binding.itemTitle.setText(item.getTitle());
        holder.binding.itemSize.setText(item.getSize());
        holder.binding.itemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.itemOnClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class PDFViewHolder extends RecyclerView.ViewHolder {

        ItemLayoutBinding binding;
        public PDFViewHolder(@NonNull ItemLayoutBinding view) {
            super(view.getRoot());
            this.binding=view;

        }

    }
}

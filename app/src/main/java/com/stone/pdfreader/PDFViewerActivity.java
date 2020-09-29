package com.stone.pdfreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import android.view.MotionEvent;

import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.stone.pdfreader.databinding.ActivityPdfViewerBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.viewModel.ViewerActivityModel;

import java.io.File;



public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,OnDrawListener, OnErrorListener,OnPageScrollListener, OnTapListener {

    ActivityPdfViewerBinding binding;
    private static PdfDto pdfItem=new PdfDto();
    private ViewerActivityModel mModel;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // bind view
        binding=ActivityPdfViewerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        //bind viewModel
        mModel= ViewModelProviders.of(this).get(ViewerActivityModel.class);

        actionBar = this.getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(pdfItem.getTitle());


        if (pdfItem!=null) {
            binding.pdfView.fromFile(new File(pdfItem.getSize()))
                    .defaultPage(mModel.getCurrentPageNumber())
                    .onPageChange(this)
                    .swipeHorizontal(mModel.isOrientation())
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .onLoad(this)
                    .onDraw(this)
                    .enableSwipe(true)
                    .onError(this)
                    .enableDoubletap(true)
                    .onPageScroll(this)
                    .onTap(this)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .load();
        }

    }
    public static Intent gotoViewerActivity(Context context, PdfDto item){
        Intent intent=new Intent(context,PDFViewerActivity.class);
        pdfItem=item;
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.orientation_menu,menu);
        @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable=getResources().getDrawable(R.drawable.ic_hori_verti);
        drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
        menu.findItem(R.id.orientation).setIcon(drawable);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
            case R.id.orientation:
                doOrientation();
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void doOrientation() {
        
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        CharSequence[] charSequences={"Horizontal","Vertical"};

        builder.setTitle("Change Orientation");
        builder.setSingleChoiceItems(charSequences, mModel.getOrientationIndex(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mModel.setOrientationIndex(i);


            }
        })
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (mModel.getOrientationIndex()==0){
                    mModel.setOrientation(true);
                }else {
                    mModel.setOrientation(false);
                }
                recreate();

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
       // pageNumber = page;
        mModel.setCurrentPageNumber(page);
       // setTitle(String.format("%s %s / %s", "bnm", page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
//        PdfDocument.Meta meta = binding.pdfView.getDocumentMeta();
//        printBookmarksTree(binding.pdfView.getTableOfContents(), "-");

    }

    @Override
    public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onPageScrolled(int page, float positionOffset) {

    }

    @Override
    public boolean onTap(MotionEvent e) {
        if (actionBar.isShowing()){
            actionBar.hide();
        }else {
            actionBar.show();
        }
        return true;
    }
}
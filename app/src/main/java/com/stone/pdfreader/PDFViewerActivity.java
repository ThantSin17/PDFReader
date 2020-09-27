package com.stone.pdfreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.shockwave.pdfium.PdfDocument;
import com.stone.pdfreader.databinding.ActivityPdfViewerBinding;
import com.stone.pdfreader.dto.PdfDto;
import com.stone.pdfreader.viewModel.ViewerActivityModel;

import java.io.File;
import java.util.List;

public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener,OnDrawListener, OnErrorListener,OnPageScrollListener {

    private int currentPage=0;
    ActivityPdfViewerBinding binding;
    private static PdfDto pdfItem=new PdfDto();
    private ViewerActivityModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // bind view
        binding=ActivityPdfViewerBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        //bind viewModel
        mModel= ViewModelProviders.of(this).get(ViewerActivityModel.class);
        currentPage=mModel.getCurrentPageNumber();


        Toast.makeText(this, pdfItem.getUrl(), Toast.LENGTH_SHORT).show();
        if (pdfItem!=null) {
            binding.pdfView.fromFile(new File(pdfItem.getUrl()))
                    .defaultPage(currentPage)
                    .onPageChange(this)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .onLoad(this)
                    .onDraw(this)
                    .enableSwipe(true)
                    .onError(this)
                    .enableDoubletap(true)
                    .onPageScroll(this)
                    .load();
        }

    }
    public static Intent gotoViewerActivity(Context context, PdfDto item){
        Intent intent=new Intent(context,PDFViewerActivity.class);
        pdfItem=item;
        return intent;
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
       // pageNumber = page;
        mModel.setCurrentPageNumber(page);
       // setTitle(String.format("%s %s / %s", "bnm", page + 1, pageCount));
    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = binding.pdfView.getDocumentMeta();
        printBookmarksTree(binding.pdfView.getTableOfContents(), "-");

    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            //Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
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
}
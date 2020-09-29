package com.stone.pdfreader;

import android.os.Environment;

import com.stone.pdfreader.dto.PdfDto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PDFManager {
    final String MEDIA_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/";
    private List<PdfDto> pdfList = new ArrayList<>();

    // Constructor


    public PDFManager() {
    }

    /**
     * Function to read all mp3 files and store the details in
     * ArrayList
     * */
    public ArrayList<PdfDto> getPlayList() {
        System.out.println(MEDIA_PATH);
        File home = new File(MEDIA_PATH);
        File[] listFiles = home.listFiles();
        if (listFiles != null && listFiles.length > 0) {
            for (File file : listFiles) {
                System.out.println(file.getAbsolutePath());
                if (file.isDirectory()) {
                    scanDirectory(file);
                } else {
                    addSongToList(file);
                }
            }
        }
        // return songs list array
        return (ArrayList<PdfDto>) pdfList;
    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        addSongToList(file);
                    }

                }
            }
        }
    }

    private void addSongToList(File song) {
        if (song.getName().endsWith(".pdf")) {
            PdfDto item = new PdfDto();
            item.setTitle(song.getName());
            item.setSize(song.getAbsolutePath());
            item.setUrl(song.getAbsolutePath());

            pdfList.add(item);
        }
    }
}

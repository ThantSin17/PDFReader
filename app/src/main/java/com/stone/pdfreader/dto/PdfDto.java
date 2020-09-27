package com.stone.pdfreader.dto;

public class PdfDto {
    private String title,url,size;

    public PdfDto(String title, String url, String size) {
        this.title = title;
        this.url = url;
        this.size = size;
    }

    public PdfDto() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}

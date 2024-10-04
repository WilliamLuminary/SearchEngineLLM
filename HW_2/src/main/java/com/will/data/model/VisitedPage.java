package com.will.data.model;

public class VisitedPage {

    private final String url;
    private final int size;
    private final int noOutLinks;
    private final String contentType;

    public VisitedPage(String url, int size, int noOutLinks, String contentType) {
        this.url = url;
        this.size = size;
        this.noOutLinks = noOutLinks;
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public int getSize() {
        return size;
    }

    public int getNoOutLinks() {
        return noOutLinks;
    }

    public String getContentType() {
        return contentType;
    }
}

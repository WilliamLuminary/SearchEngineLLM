package com.will.data.model;

public class FetchResult {

    private final String url;
    private final int statusCode;

    public FetchResult(String url, int statusCode) {
        this.url = url;
        this.statusCode = statusCode;
    }

    public String getUrl() {
        return url;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

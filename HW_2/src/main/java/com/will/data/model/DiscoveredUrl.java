package com.will.data.model;

public class DiscoveredUrl {

    private final String url;
    private final boolean isFetching;
    private final boolean isWithinWebsite;

    public DiscoveredUrl(String url, boolean isFetching, boolean isWithinWebsite) {
        this.url = url;
        this.isFetching = isFetching;
        this.isWithinWebsite = isWithinWebsite;
    }

    public String getUrl() {
        return url;
    }

    public boolean isFetching() {
        return isFetching;
    }

    public boolean isWithinWebsite() {
        return isWithinWebsite;
    }
}
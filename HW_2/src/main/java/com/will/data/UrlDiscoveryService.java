package com.will.data;

import com.will.data.model.DiscoveredUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlDiscoveryService {
    private final Map<String, DiscoveredUrl> urlMap = new HashMap<>();
    private final List<DiscoveredUrl> discoveredUrls = new ArrayList<>();


    public void addDiscoveredUrl(DiscoveredUrl url) {
        synchronized (this) {
            if (!urlMap.containsKey(url.getUrl())) {
                discoveredUrls.add(url);
                urlMap.put(url.getUrl(), url);
            }
        }
    }

    public DiscoveredUrl findUrl(String url) {
        synchronized (this) {
            return urlMap.get(url);
        }
    }

    public List<DiscoveredUrl> getDiscoveredUrls() {
        return discoveredUrls;
    }
}

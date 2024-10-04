package com.will.data;

import com.will.data.model.FetchResult;

import java.util.ArrayList;
import java.util.List;

public class FetchStatusTracker {
//    private final List<FetchResult> fetchResults = new ArrayList<>();


    private final List<FetchResult> fetchResults = new ArrayList<>();


    public void addFetchResult(FetchResult fetchResult) {
        synchronized (this) {
            fetchResults.add(fetchResult);
        }
    }

    public List<FetchResult> getFetchResults() {
        return fetchResults;
    }

}

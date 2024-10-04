package com.will.data;

import com.will.data.model.VisitedPage;

import java.util.ArrayList;
import java.util.List;

public class VisitedPageTracker {
    private final List<VisitedPage> visitedPages = new ArrayList<>();


    public void addVisitedPage(VisitedPage visitedPage) {
        synchronized (this) {
            visitedPages.add(visitedPage);
        }
    }

    public List<VisitedPage> getVisitedPages() {
        return visitedPages;
    }

}

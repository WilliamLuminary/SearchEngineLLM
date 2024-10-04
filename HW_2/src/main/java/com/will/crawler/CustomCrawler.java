package com.will.crawler;

import com.will.config.Config;
import com.will.data.FetchStatusTracker;
import com.will.data.UrlDiscoveryService;
import com.will.data.VisitedPageTracker;
import com.will.data.model.DiscoveredUrl;
import com.will.data.model.FetchResult;
import com.will.data.model.VisitedPage;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

public class CustomCrawler extends WebCrawler {

    private static final Pattern DOC_PATTERNS = Pattern.compile(".*(\\.(html?|php|pdf|docx?))$");
    private static final Pattern IMAGE_PATTERNS = Pattern.compile(".*(\\.(jpe?g|ico|png|bmp|svg|gif|webp|tiff))$");

    private final FetchStatusTracker fetchStatusTracker;
    private final UrlDiscoveryService urlDiscoveryService;
    private final VisitedPageTracker visitedPageTracker;

    public CustomCrawler(
            FetchStatusTracker fetchStatusTracker,
            UrlDiscoveryService urlDiscoveryService,
            VisitedPageTracker visitedPageTracker
    ) {
        this.fetchStatusTracker = fetchStatusTracker;
        this.urlDiscoveryService = urlDiscoveryService;
        this.visitedPageTracker = visitedPageTracker;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String urlString = url.getURL();
        DiscoveredUrl existingUrl = urlDiscoveryService.findUrl(urlString);
        if (existingUrl == null) {
            boolean withinWebsite = hasRequiredHostname(url);
            boolean shouldFetch = withinWebsite && (!hasExtension(url) || hasRequiredExtension(url));
            DiscoveredUrl discoveredUrl = new DiscoveredUrl(urlString, shouldFetch, withinWebsite);
            urlDiscoveryService.addDiscoveredUrl(discoveredUrl);
            return shouldFetch;
        } else {
            // Already discovered, no need to revisit
            return false;
        }
    }

    private boolean hasRequiredHostname(WebURL url) {
        String domain = url.getDomain();
        String subdomain = url.getSubDomain();
        if (subdomain.isEmpty()) {
            subdomain = "www";
        }
        String hostname = String.format("%s.%s", subdomain, domain);
        return hostname.equals(Config.WEBSITE_HOSTNAME);
    }

    private boolean hasExtension(WebURL url) {
        String path = url.getPath();
        String filename = path.substring(path.lastIndexOf("/") + 1);
        return filename.contains(".");
    }

    private boolean hasRequiredExtension(WebURL url) {
        String path = url.getPath().toLowerCase();
        return DOC_PATTERNS.matcher(path).matches() || IMAGE_PATTERNS.matcher(path).matches();
    }

    @Override
    protected void handlePageStatusCode(WebURL webUrl, int statusCode, String statusDescription) {
        String url = webUrl.getURL();
        FetchResult fetchResult = new FetchResult(url, statusCode);
        fetchStatusTracker.addFetchResult(fetchResult);
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        String contentType = getContentType(page);
        if (hasRequiredContentType(contentType)) {
            int size = getPageSize(page);
            int numberOfOutlinks = getNumberOfOutlinks(page);
            VisitedPage visitedPage = new VisitedPage(url, size, numberOfOutlinks, contentType);
            visitedPageTracker.addVisitedPage(visitedPage);
        }
    }

    private boolean hasRequiredContentType(String contentType) {
        contentType = contentType.toLowerCase();
        return contentType.startsWith("image")
                || contentType.equals("application/pdf")
                || contentType.equals("application/msword")
                || contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                || contentType.equals("text/html");
    }

    private int getPageSize(Page page) {
        return page.getContentData().length;
    }

    private String getContentType(Page page) {
        String contentType = page.getContentType();
        if (contentType.contains(";")) {
            contentType = contentType.substring(0, contentType.indexOf(';'));
        }
        return contentType;
    }

    private int getNumberOfOutlinks(Page page) {
        return page.getParseData().getOutgoingUrls().size();
    }
}

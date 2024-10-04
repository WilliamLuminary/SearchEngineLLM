package com.will.report;

import com.will.config.Config;
import com.will.data.FetchStatusTracker;
import com.will.data.UrlDiscoveryService;
import com.will.data.VisitedPageTracker;
import com.will.data.model.DiscoveredUrl;
import com.will.data.model.FetchResult;
import com.will.data.model.VisitedPage;
import com.will.util.FileUtils;
import org.apache.commons.httpclient.HttpStatus;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummaryReport {
    private static final int KB = 1024;

    private final FetchStatusTracker fetchStatusTracker;
    private final UrlDiscoveryService urlDiscoveryService;
    private final VisitedPageTracker visitedPageTracker;

    public SummaryReport(
            FetchStatusTracker fetchStatusTracker,
            UrlDiscoveryService urlDiscoveryService,
            VisitedPageTracker visitedPageTracker
    ) {
        this.fetchStatusTracker = fetchStatusTracker;
        this.urlDiscoveryService = urlDiscoveryService;
        this.visitedPageTracker = visitedPageTracker;
    }

    public void write() throws IOException {
        String filepath = Config.SUMMARY_FILE_PATH;

        try (FileWriter fileWriter = new FileWriter(filepath)) {
            FileUtils.createFile(filepath);

            writeHeader(fileWriter);
            writeNewLine(fileWriter);

            writeStatisticsHeader(fileWriter, "Fetch Statistics");
            writeFetchStatistics(fileWriter);
            writeNewLine(fileWriter);

            writeStatisticsHeader(fileWriter, "Outgoing URLs");
            writeUrlStatistics(fileWriter);
            writeNewLine(fileWriter);

            writeStatisticsHeader(fileWriter, "Status Codes");
            writeStatusCodeStatistics(fileWriter);
            writeNewLine(fileWriter);

            writeStatisticsHeader(fileWriter, "File Sizes");
            writePageSizeStatistics(fileWriter);
            writeNewLine(fileWriter);

            writeStatisticsHeader(fileWriter, "Content Types");
            writeContentTypeStatistics(fileWriter);
        }
    }

    private void writeHeader(FileWriter fileWriter) throws IOException {
        writeEntry(fileWriter, "Name", Config.STUDENT_NAME);
        writeEntry(fileWriter, "USC ID", Config.USC_ID);
        writeEntry(fileWriter, "News site crawled", String.format("%s.com", Config.WEBSITE_DOMAIN));
        writeEntry(fileWriter, "Number of threads", Config.NUMBER_OF_CRAWLERS);
    }

    private void writeFetchStatistics(FileWriter fileWriter) throws IOException {
        List<FetchResult> items = fetchStatusTracker.getFetchResults();
        long attemptedFetches = items.size();
        long successfulFetches = getNoSucFetches(items);
        long failedOrAbortedFetches = attemptedFetches - successfulFetches;

        writeEntry(fileWriter, "# fetches attempted", attemptedFetches);
        writeEntry(fileWriter, "# fetches succeeded", successfulFetches);
        writeEntry(fileWriter, "# fetches failed or aborted", failedOrAbortedFetches);
    }

    private void writeUrlStatistics(FileWriter fileWriter) throws IOException {
        List<DiscoveredUrl> discovered = urlDiscoveryService.getDiscoveredUrls();
        List<VisitedPage> visited = visitedPageTracker.getVisitedPages();
        long urls = getNoOutLinks(visited);
        long uniqueUrls = getNoUniqUrls(discovered);
        long withinWebsiteUrls = getNoInUniqUrls(discovered);
        long outsideWebsiteUrls = uniqueUrls - withinWebsiteUrls;

        writeEntry(fileWriter, "Total URLs extracted", urls);
        writeEntry(fileWriter, "# unique URLs extracted", uniqueUrls);
        writeEntry(fileWriter, "# unique URLs within News Site", withinWebsiteUrls);
        writeEntry(fileWriter, "# unique URLs outside News Site", outsideWebsiteUrls);
    }

    private void writeStatusCodeStatistics(FileWriter fileWriter) throws IOException {
        List<FetchResult> items = fetchStatusTracker.getFetchResults();
        Map<Integer, Long> statusCodes = getNoStatusCodes(items);
        for (Integer statusCode : statusCodes.keySet().stream().sorted().collect(Collectors.toList())) {
            String statusCodeDescription = String.format("%s %s", statusCode, HttpStatus.getStatusText(statusCode));
            writeEntry(fileWriter, statusCodeDescription, statusCodes.get(statusCode));
        }
    }

    private void writePageSizeStatistics(FileWriter fileWriter) throws IOException {
        List<VisitedPage> items = visitedPageTracker.getVisitedPages();
        writeEntry(fileWriter, "< 1KB", getNoPageSize(items, 0, KB));
        writeEntry(fileWriter, "1KB ~ <10KB", getNoPageSize(items, KB, 10 * KB));
        writeEntry(fileWriter, "10KB ~ <100KB", getNoPageSize(items, 10 * KB, 100 * KB));
        writeEntry(fileWriter, "100KB ~ <1MB", getNoPageSize(items, 100 * KB, 1000 * KB));
        writeEntry(fileWriter, ">= 1MB", getNoPageSize(items, 1000 * KB, Integer.MAX_VALUE));
    }

    private void writeContentTypeStatistics(FileWriter fileWriter) throws IOException {
        List<VisitedPage> items = visitedPageTracker.getVisitedPages();
        Map<String, Long> contentTypes = getNoContentTypes(items);
        for (Map.Entry<String, Long> entry : contentTypes.entrySet()) {
            writeEntry(fileWriter, entry.getKey(), entry.getValue());
        }
    }

    private void writeStatisticsHeader(FileWriter fileWriter, String header) throws IOException {
        fileWriter.write(String.format("%s:\n", header));
        StringBuilder separator = new StringBuilder();
        separator.append("=".repeat(Math.max(0, header.length() + 1)));
        fileWriter.write(String.format("%s\n", separator));
    }

    private void writeNewLine(FileWriter fileWriter) throws IOException {
        fileWriter.write("\n");
    }

    private void writeEntry(FileWriter fileWriter, String statistic, Object value) throws IOException {
        fileWriter.write(String.format("%s: %s\n", statistic, value.toString()));
    }

    private long getNoSucFetches(List<FetchResult> items) {
        return items.stream().filter(item -> item.getStatusCode() == 200).count();
    }

    private long getNoOutLinks(List<VisitedPage> items) {
        return items.stream().mapToLong(VisitedPage::getNoOutLinks).sum();
    }

    private long getNoUniqUrls(List<DiscoveredUrl> items) {
        return items.stream().map(DiscoveredUrl::getUrl).distinct().count();
    }

    private long getNoInUniqUrls(List<DiscoveredUrl> items) {
        return items.stream().filter(DiscoveredUrl::isWithinWebsite).map(DiscoveredUrl::getUrl)
                    .distinct().count();
    }

    private Map<Integer, Long> getNoStatusCodes(List<FetchResult> items) {
        return items.stream()
                    .collect(Collectors.groupingBy(FetchResult::getStatusCode, Collectors.counting()));
    }

    private long getNoPageSize(List<VisitedPage> items, Integer min, Integer max) {
        return items.stream().map(VisitedPage::getSize).filter(size -> size >= min && size < max).count();
    }

    private Map<String, Long> getNoContentTypes(List<VisitedPage> items) {
        return items.stream()
                    .collect(Collectors.groupingBy(VisitedPage::getContentType, Collectors.counting()));
    }
}

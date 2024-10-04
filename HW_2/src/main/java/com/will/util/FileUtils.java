package com.will.util;

import com.opencsv.CSVWriter;
import com.will.data.model.DiscoveredUrl;
import com.will.data.model.FetchResult;
import com.will.data.model.VisitedPage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileUtils {

    public static void createFile(String filepath) throws IOException {
        Path path = Paths.get(filepath);
        Files.createDirectories(path.getParent());
        Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).close();
    }


    public static CSVWriter getCSVWriter(String filepath) throws IOException {
        createFile(filepath);
        FileWriter fileWriter = new FileWriter(filepath);
        return new CSVWriter(fileWriter, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
    }

    public static void writeDiscoveredUrls(String filepath, List<DiscoveredUrl> urls) throws IOException {
        try (CSVWriter csvWriter = getCSVWriter(filepath)) {
            csvWriter.writeNext(new String[]{"URL", "Indicator"});
            for (var url : urls) {
                csvWriter.writeNext(new String[]{
                        formatUrl(url.getUrl()),
                        url.isFetching() ? "OK" : "N_OK"
                });
            }
        }
    }

    public static void writeFetchResults(String filepath, List<FetchResult> results) throws IOException {
        try (CSVWriter csvWriter = getCSVWriter(filepath)) {
            csvWriter.writeNext(new String[]{"URL", "Status"});
            for (FetchResult result : results) {
                csvWriter.writeNext(new String[]{
                        formatUrl(result.getUrl()),
                        String.valueOf(result.getStatusCode())
                });
            }
        }
    }

    public static void writeVisitedPages(String filepath, List<VisitedPage> pages) throws IOException {
        try (CSVWriter csvWriter = getCSVWriter(filepath)) {
            csvWriter.writeNext(new String[]{"URL", "Size (Bytes)", "# of Outlinks", "Content Type"});
            for (VisitedPage page : pages) {
                csvWriter.writeNext(new String[]{
                        formatUrl(page.getUrl()),
                        String.valueOf(page.getSize()),
                        String.valueOf(page.getNoOutLinks()),
                        page.getContentType()
                });
            }
        }
    }

    public static String formatUrl(String url) {
        return url.replace(",", "-");
    }
}

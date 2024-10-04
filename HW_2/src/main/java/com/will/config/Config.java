package com.will.config;

import java.nio.file.Path;

public class Config {

    // Student Info
    public static final String STUDENT_NAME = "Yaxing Li";
    public static final String USC_ID = "5487590475";

    // Website Info
    public static final String NEWS_SITE = "usatoday";
    public static final String WEBSITE_HOSTNAME = "www." + NEWS_SITE + ".com";
    public static final String SEED_URL = "https://" + WEBSITE_HOSTNAME;

    // CustomCrawler Configuration
    public static final int NUMBER_OF_CRAWLERS = 7;
    public static final int MAX_DEPTH_OF_CRAWLING = 16;
    public static final int MAX_PAGES_TO_FETCH = 20000;

    // Path Configuration
    public static final String DATA_BASE_PATH = "data";

    // File Paths
    public static final String CRAWL_FILE_PATH = buildFilePath(DATA_BASE_PATH, "crawler4j");
    public static final String DISCOVERED_FILE_PATH = buildFilePath(DATA_BASE_PATH, "urls_" + NEWS_SITE + ".csv");
    public static final String FETCHING_FILE_PATH = buildFilePath(DATA_BASE_PATH, "fetch_" + NEWS_SITE + ".csv");
    public static final String VISITED_FILE_PATH = buildFilePath(DATA_BASE_PATH, "visit_" + NEWS_SITE + ".csv");
    public static final String SUMMARY_FILE_PATH = buildFilePath(DATA_BASE_PATH, "CrawlReport_" + NEWS_SITE + ".txt");

    // Helper method to build file paths
    private static String buildFilePath(String... parts) {
        return Path.of("", parts).toString();
    }
}
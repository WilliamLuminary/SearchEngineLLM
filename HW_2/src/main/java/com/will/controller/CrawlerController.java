package com.will.controller;

import com.will.config.Config;
import com.will.crawler.CustomCrawler;
import com.will.data.FetchStatusTracker;
import com.will.data.UrlDiscoveryService;
import com.will.data.VisitedPageTracker;
import com.will.report.SummaryReport;
import com.will.util.FileUtils;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerController {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    public static void main(String[] args) throws Exception {
        try {
            FetchStatusTracker fetchStatusTracker = new FetchStatusTracker();
            UrlDiscoveryService urlDiscoveryService = new UrlDiscoveryService();
            VisitedPageTracker visitedPageTracker = new VisitedPageTracker();

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(Config.CRAWL_FILE_PATH);
            config.setMaxDepthOfCrawling(Config.MAX_DEPTH_OF_CRAWLING);
            config.setMaxPagesToFetch(Config.MAX_PAGES_TO_FETCH);
            config.setIncludeBinaryContentInCrawling(true);

            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            controller.addSeed(Config.SEED_URL);

            controller.start(() -> new CustomCrawler(
                    fetchStatusTracker,
                    urlDiscoveryService,
                    visitedPageTracker
            ), Config.NUMBER_OF_CRAWLERS);

            FileUtils.writeDiscoveredUrls(Config.DISCOVERED_FILE_PATH, urlDiscoveryService.getDiscoveredUrls());
            FileUtils.writeFetchResults(Config.FETCHING_FILE_PATH, fetchStatusTracker.getFetchResults());
            FileUtils.writeVisitedPages(Config.VISITED_FILE_PATH, visitedPageTracker.getVisitedPages());

            SummaryReport summaryReport = new SummaryReport(
                    fetchStatusTracker,
                    urlDiscoveryService,
                    visitedPageTracker
            );
            summaryReport.write();
        } catch (Exception e) {
            logger.error("Error occurred during crawling: {}", e.getMessage(), e);
        }
    }
}

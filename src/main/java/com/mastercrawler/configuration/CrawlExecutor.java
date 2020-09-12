package com.mastercrawler.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mastercrawler.domain.ScraperResult;
import com.mastercrawler.repository.ScrapeMongoReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CrawlExecutor {

	private static final Logger log = LoggerFactory.getLogger(CrawlExecutor.class);

	@Autowired
	private ScrapeMongoReactiveRepository scrapeMongoReactiveRepository;

	@Autowired
	private CrawlerParameters crawlerParameters;

	@Value("${crawler.categoryList1}")
	private String categoryList1;

	@Value("${crawler.categoryList2}")
	private String categoryList2;

	@Value("${crawler.selectorName}")
	private String selectorName;

	@Value("${crawler.selectorRating}")
	private String selectorRating;

	@Value("${crawler.selectorCountOfReview}")
	private String selectorCountOfReview;

	@Value("${crawler.selectorTypeOfFood}")
	private String selectorTypeOfFood;

	@Value("${crawler.selectorMinOrderValue}")
	private String selectorMinOrderValue;
	
	@Value("${crawler.timeoutPerRequest}")
	private int timeoutPerRequest;

	public boolean statusCrawl = false;
	
	public CrawlExecutor(ScrapeMongoReactiveRepository scrapeMongoReactiveRepository,
			CrawlerParameters crawlerParameters) {
		this.scrapeMongoReactiveRepository = scrapeMongoReactiveRepository;
		this.crawlerParameters = crawlerParameters;
	}

	public long start = System.nanoTime();

	@Scheduled(fixedDelayString = "${crawler.milisecSheduleTaskDelay}", initialDelay = 0)
	public void crawl() {

		log.info(" ========== Requests created {} ", start);

		crawlResult();
	}

	private void setCrawlStatus(boolean newStatusCrawler) {

		if (newStatusCrawler) {
			log.info(" ========== Crawl finished {} ", Duration.ofNanos(System.nanoTime() - start));
		}
	}

	private void crawlResult() {

		var idSupplier = getIdSequenceSupplier();
		scrapeMongoReactiveRepository
				.saveAll(crawlResults(Flux.just(new CrawlerDataAlgorithm(crawlerParameters.getSeed(), 1)),
						crawlerParameters.getDepth())
								.distinct(res -> res.getTitle())
								.map(result -> new ScraperResult(idSupplier.get(), result.getUrl(), result.getTitle(),
										result.getRating(), result.getCountReviews(), result.getTypeOfFoods(),
										result.getMinOrderValue()))
								.filter(res -> !res.getNameResult().isEmpty() && !res.getUrl().isEmpty() && !res.getRating().isEmpty()))
				.delayElements(Duration.ofMillis(timeoutPerRequest))
				.subscribe(scrapers -> log.info("Time per request - ({})", Duration.ofNanos(System.nanoTime() - start)));
		statusCrawl = true;
		setCrawlStatus(statusCrawl);
	}

	public Flux<CrawlerResult> crawlResults(Flux<CrawlerDataAlgorithm> commands, int maxDepth) {

		return commands.filter(command -> command.getDepth() <= maxDepth)
				.filter(command -> command.getSeed().startsWith(crawlerParameters.getSeed()))
				.distinct(CrawlerDataAlgorithm::getSeed).flatMap(this::getResult)
				.flatMap(result -> getCrawlResults(result, maxDepth));
	}

	public Flux<CrawlerResult> getCrawlResults(CrawlerResult result, int maxDepth) {

		return Mono.just(result).mergeWith(crawlResults(getHyperlinksFromCrawl(result), maxDepth));
	}

	// get hyperlinks
	private Flux<CrawlerDataAlgorithm> getHyperlinksFromCrawl(CrawlerResult result) {

		return Flux.fromIterable(result.getHyperlinks())
				.map(link -> new CrawlerDataAlgorithm(link, result.getDepth() + 1));
	}

	private Mono<CrawlerResult> getResult(CrawlerDataAlgorithm command) {

		return Mono.just(command).map(CrawlerDataAlgorithm::getSeed).flatMap(this::getDocument)
				.flatMap(document -> getCrawlerResult(document, command.getDepth()));
	}

	// scrape crawl results
	private Mono<CrawlerResult> getCrawlerResult(Document document, int depth) {

		String categoryString = "";
		Elements el = document.select(categoryList1);
		if (el.size() == 0) {
			el = document.select(categoryList2);
			categoryString = categoryList2;
		} else {
			categoryString = categoryList1;
		}

		return Flux.fromIterable(document.select(categoryString)).map(element -> element.absUrl("href")).collectList()
				.map(hyperlinks -> new CrawlerResult(document.location(), document.select(selectorName).text(),
						document.select(selectorRating).text(), document.select(selectorCountOfReview).text(),
						document.select(selectorTypeOfFood).text(), document.select(selectorMinOrderValue).text(),
						hyperlinks, depth));
	}

	private Mono<Document> getDocument(String seed) {
		try {
			// request for document
			return Mono.just(Jsoup.connect(seed).headers(setRequestHeaders()).userAgent(generateUserAgent())
					.validateTLSCertificates(false).get());
		} catch (IOException | IllegalArgumentException e) {
			log.debug("No data for seed {}", seed, e);
			return Mono.empty();
		}
	}

	private Map<String, String> setRequestHeaders() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.put("Connection", "keep-alive");
		headers.put("Accept-Encoding", "gzip, deflate, sdch, br");
		return headers;
	}

	private String generateUserAgent() {
		List<String> userAgents = new ArrayList<String>();
		Random randomNumber = new Random();
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("useragents")))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				userAgents.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userAgents.get(randomNumber.nextInt(userAgents.size()));
	}

	private Supplier<String> getIdSequenceSupplier() {
		return new Supplier<>() {
			Long l = 0L;

			@Override
			public String get() {
				return String.format("%05d", l++);
			}
		};
	}

}

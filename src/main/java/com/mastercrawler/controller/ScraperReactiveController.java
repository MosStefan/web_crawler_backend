package com.mastercrawler.controller;

import java.time.Duration;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercrawler.domain.ScraperResult;
import com.mastercrawler.repository.ScrapeMongoReactiveRepository;

import reactor.core.publisher.Flux;

@RestController
public class ScraperReactiveController {
	 private static final int DELAY_PER_ITEM_MS = 100;

	    
	    private final ScrapeMongoReactiveRepository scrapeMongoReactiveRepository;


	    public ScraperReactiveController(final ScrapeMongoReactiveRepository scrapeMongoReactiveRepository) {
	        this.scrapeMongoReactiveRepository = scrapeMongoReactiveRepository;
	    }

	    @GetMapping("/scraper-results-reactive")
	    public Flux<ScraperResult> getScrapeResultsFlux(
	    		@RequestParam(name= "regionSearch") String regionSearch,
	    		@RequestParam(name= "ratingSearch") String ratingSearch) {
	        return scrapeMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc(regionSearch,ratingSearch).delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
	    }
	    
	    @GetMapping("/scraper-results")
	    public Flux<ScraperResult> getScrapeFlux() {
	        return scrapeMongoReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
	    }
	    

	    @GetMapping("/scraper-results-reactive-paged")
	    public Flux<ScraperResult> getScrapeResultsFlux(
	    		                        @RequestParam(name= "regionSearch") String regionSearch,
	    		                        @RequestParam(name= "ratingSearch") String ratingSearch,
	    		                        final @RequestParam(name = "page") int page,
	                                    final @RequestParam(name = "size") int size) {
			Flux<ScraperResult> flux = scrapeMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc(regionSearch, ratingSearch, PageRequest.of(page, size))
	                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS));
			 return  flux;
	    }
}

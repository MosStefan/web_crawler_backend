package com.mastercrawler.crawlerresult;

import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;

import com.mastercrawler.domain.ScraperResult;
import com.mastercrawler.repository.ScrapeMongoReactiveRepository;

import static org.mockito.BDDMockito.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CrawlerResultTest {
		
    @MockBean
    private ScrapeMongoReactiveRepository scraperResultMongoReactiveRepository;

    private WebClient webClient;

    private Flux<ScraperResult> scrapeResults;
    
    @LocalServerPort
    private int defaultPort;

    @Before
    public void setUp() {
        this.webClient = WebClient.create("http://localhost:" + defaultPort);
        scrapeResults = Flux.just(
                new ScraperResult("00002", "https://www.donesi.com/dostava/brands/bigpizza","BigPizza Vracar", "4.2", "1541", "Pica", "590"),
                new ScraperResult("00008", "https://www.donesi.com/dostava/brands/cookie", "Cooker Vracar", "4.5", "617", "Burgeri", "100"),
                new ScraperResult("000018", "https://www.donesi.com/dostava/brands/walter", "Intergalactic", "4.1", "388", "Burgeri", "250"));
    }

    @Test
    public void reactiveRequest() {

        given(scraperResultMongoReactiveRepository.findAll()).willReturn(scrapeResults);

        Flux<ScraperResult> scrapeFluxResults = webClient.get().uri("/scraper-results").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(ScraperResult.class));

        StepVerifier.create(scrapeFluxResults)
                .expectNext(new ScraperResult("00002", "https://www.donesi.com/dostava/brands/bigpizza", "BigPizza Vracar", "4.2", "1541", "Pica", "590"))
                		.expectNext(new ScraperResult("00008", "https://www.donesi.com/dostava/brands/cookie", "Cooker Vracar", "4.5", "617", "Burgeri", "100"))
                		.expectNext(new ScraperResult("000018", "https://www.donesi.com/dostava/brands/walter", "Intergalactic", "4.1", "388", "Burgeri", "250"))
                .expectComplete()
                .verify();
    }

    @Test
    public void reactivePagedRequest() {
        given(scraperResultMongoReactiveRepository.findAllByIdNotNullOrderByIdAsc("Vracar", "4", PageRequest.of(1, 2)))
                .willReturn(scrapeResults.take(2));

        Flux<ScraperResult> scrapeFluxResults = webClient.get().uri("/scraper-results-reactive-paged?regionSearch=Vracar&ratingSearch=4&page=1&size=2")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange().flatMapMany(response -> response.bodyToFlux(ScraperResult.class));

        StepVerifier.create(scrapeFluxResults)
        .expectNext(new ScraperResult("00002", "https://www.donesi.com/dostava/brands/bigpizza", "BigPizza Vracar", "4.2", "1541", "Pica", "590"))
        .expectNext(new ScraperResult("00008", "https://www.donesi.com/dostava/brands/cookie", "Cooker Vracar", "4.5", "617", "Burgeri", "100"))
                .expectComplete()
                .verify();       
    }

}

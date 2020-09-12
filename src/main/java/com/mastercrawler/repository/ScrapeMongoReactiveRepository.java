package com.mastercrawler.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import com.mastercrawler.domain.ScraperResult;
import reactor.core.publisher.Flux;


public interface ScrapeMongoReactiveRepository  extends ReactiveSortingRepository<ScraperResult, String> {

    @Query("{ '$or': [ { 'nameResult': { '$regex': ?0 } }], '$and': [{ 'rating' : { '$gt' : ?1 }}] }")
    Flux<ScraperResult> findAllByIdNotNullOrderByIdAsc(String regionSearch, String ratingSearch, final Pageable page);
    
    @Query("{ '$or': [ { 'nameResult': { '$regex': ?0 } }], '$and': [{ 'rating' : { '$gt' : ?1 }}] }")
    Flux<ScraperResult> findAllByIdNotNullOrderByIdAsc(String regionSearch, String ratingSearch);

}

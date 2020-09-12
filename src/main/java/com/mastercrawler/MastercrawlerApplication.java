package com.mastercrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.context.annotation.Bean;

import org.springframework.scheduling.annotation.EnableScheduling;

import com.mastercrawler.configuration.CrawlerParameters;



@SpringBootApplication
@EnableScheduling
public class MastercrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MastercrawlerApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "crawler")
	public CrawlerParameters crawlerParameters() {
		return new CrawlerParameters();
	}

}

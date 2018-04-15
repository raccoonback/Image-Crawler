package com.landvibe.hackday;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class ImageCrawlerApplication {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "/Users/koseungbin/Downloads/chromedriver");
		SpringApplication.run(ImageCrawlerApplication.class, args);
	}
}

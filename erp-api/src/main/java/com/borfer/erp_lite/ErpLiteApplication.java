package com.borfer.erp_lite;

import com.borfer.erp_lite.persistence.mongo.document.CatalogDocument;
import com.borfer.erp_lite.persistence.mongo.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ErpLiteApplication implements CommandLineRunner {

	@Autowired
	private CatalogRepository catalogRepository;

	public static void main(String[] args) {
		SpringApplication.run(ErpLiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.catalogRepository.findAll().stream().map(CatalogDocument::getName).forEach(System.out::println);
	}
}

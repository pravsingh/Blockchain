package com.hashfold.blockchain.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hashfold.blockchain.config.BlockchainConfig;

/**
 * Starts WebService and initializes SpringBoot framework.
 * 
 * @author Praveendra Singh
 *
 */
@SpringBootApplication(scanBasePackageClasses = { BlockchainConfig.class })
public class BlockchainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlockchainApplication.class, args);
	}
}

package com.hashfold.blockchain.app;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashfold.blockchain.api.BlockchainController;
import com.hashfold.blockchain.domain.Transaction;
import com.hashfold.blockchain.model.ChainResponse;
import com.hashfold.blockchain.model.MineResponse;
import com.hashfold.blockchain.model.TransactionResponse;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 
 * @author Praveendra Singh
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class BlockchainApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private ObjectMapper mapper;

	private RestTemplate client;

	private String baseUrl;

	@Before
	public void init() {

		OkHttp3ClientHttpRequestFactory rf = new OkHttp3ClientHttpRequestFactory();
		rf.setConnectTimeout(1 * 1000);
		rf.setReadTimeout(1000 * 1000);

		client = new RestTemplate(rf);

		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void mineBlocksAddTransactionsAndFetchChainDetailsTest() {

		int numberOfBlocksToMine = 3;

		for (int i = 0; i < numberOfBlocksToMine; i++) {

			Transaction transaction = Transaction.builder().sender("s" + i).recipient("r" + i)
					.amount(BigDecimal.valueOf(i)).build();

			TransactionResponse txnResponse = client.postForObject(baseUrl + "/transactions", transaction,
					TransactionResponse.class);

			MineResponse mineResponse = client.getForObject(baseUrl + "/mine", MineResponse.class);

			log.info("mineResponse={}, txnResponse={}", mineResponse, txnResponse);
		}

		ChainResponse chainResponse = client.getForObject(baseUrl + "/chain", ChainResponse.class);
		log.info("chainResponse={}", chainResponse);

		Assert.assertEquals(Long.valueOf(numberOfBlocksToMine + 1), Long.valueOf(chainResponse.getLength()));
	}

	@Test
	public void blockMiningTest() throws IOException {

		Request request = new Request.Builder().url(baseUrl + "/mine").build();
		Response response = new OkHttpClient().newCall(request).execute();

		if (response.code() != 200) {
			Assert.fail();
		}

		MineResponse mined = mapper.readValue(response.body().string(), MineResponse.class);
		Assert.assertNotNull(mined);
		Assert.assertEquals(BlockchainController.NODE_ACCOUNT_ADDRESS, mined.getTransactions().get(0).getSender());

	}

}

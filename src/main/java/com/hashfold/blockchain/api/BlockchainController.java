package com.hashfold.blockchain.api;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hashfold.blockchain.domain.Block;
import com.hashfold.blockchain.domain.Transaction;
import com.hashfold.blockchain.model.ChainResponse;
import com.hashfold.blockchain.model.MineResponse;
import com.hashfold.blockchain.model.TransactionResponse;
import com.hashfold.blockchain.service.Blockchain;
import com.hashfold.blockchain.util.BlockProofOfWorkGenerator;

/**
 * Exposes Basic Blockchain related APIs.
 * 
 * @author Praveendra Singh
 *
 */
@RestController
@RequestMapping("/")
public class BlockchainController {

	@Autowired
	private Blockchain blockChain;

	@Autowired
	private ObjectMapper mapper;

	public static final String NODE_ID = UUID.randomUUID().toString().replace("-", "");
	public static final String NODE_ACCOUNT_ADDRESS = "0";
	public static final BigDecimal MINING_CASH_AWARD = BigDecimal.ONE;

	@GetMapping("/mine")
	public MineResponse mine() throws JsonProcessingException {

		// (1) - Calculate the Proof of Work
		Block lastBlock = blockChain.lastBlock();

		Long lastProof = lastBlock.getProof();

		Long proof = BlockProofOfWorkGenerator.proofOfWork(lastProof);

		// (2) - Reward the miner (us) by adding a transaction granting us 1
		// coin
		blockChain.addTransaction(NODE_ACCOUNT_ADDRESS, NODE_ID, MINING_CASH_AWARD);

		// (3) - Forge the new Block by adding it to the chain
		Block newBlock = blockChain.createBlock(proof, lastBlock.hash(mapper));

		return MineResponse.builder().message("New Block Forged").index(newBlock.getIndex())
				.transactions(newBlock.getTransactions()).proof(newBlock.getProof())
				.previousHsh(newBlock.getPreviousHash()).build();
	}

	@GetMapping("/chain")
	public ChainResponse fullChain() throws JsonProcessingException {
		return ChainResponse.builder().chain(blockChain.getChain()).length(blockChain.getChain().size()).build();
	}

	@PostMapping("/transactions")
	public TransactionResponse newTransaction(@RequestBody @Valid Transaction trans) throws JsonProcessingException {

		Long index = blockChain.addTransaction(trans.getSender(), trans.getRecipient(), trans.getAmount());

		return TransactionResponse.builder().index(index).build();
	}
}

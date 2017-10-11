package com.hashfold.blockchain.model;

import java.util.List;

import com.hashfold.blockchain.domain.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Holds the mined block details.
 * 
 * @author Praveendra Singh
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MineResponse {
	private String message;
	private Long index;
	private List<Transaction> transactions;
	private Long proof;
	private String previousHsh;
}

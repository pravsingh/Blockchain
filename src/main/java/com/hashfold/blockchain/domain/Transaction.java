package com.hashfold.blockchain.domain;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a transaction event in the Block.
 * 
 * @author Praveendra Singh
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	@NotEmpty
	private String sender;

	@NotEmpty
	private String recipient;

	@NotNull
	private BigDecimal amount;
}
package com.hashfold.blockchain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Holds the newly added block transaction details.
 * 
 * @author Praveendra Singh
 *
 */
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	private Long index;
}

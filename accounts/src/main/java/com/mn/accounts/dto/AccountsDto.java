package com.mn.accounts.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AccountsDto {

	@Min(value = 1_000_000_000L, message = "Account number should be 10 digits")
	@Max(value= 9_999_999_999L, message = "Account number should be 10 digits")
	private Long accountNumber;

	@NotEmpty(message = "AccountType can not be a null or empty")
	private String accountType;

	@NotEmpty(message = "BranchAddress can not be a null or empty")
	private String branchAddress;

}

package com.mn.accounts.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.mn.accounts.constants.AccountsConstants;
import com.mn.accounts.dto.AccountsDto;
import com.mn.accounts.dto.CustomerDto;
import com.mn.accounts.entity.Accounts;
import com.mn.accounts.entity.Customer;
import com.mn.accounts.exception.CustomerAlreadyExistsException;
import com.mn.accounts.exception.ResourceNotFoundException;
import com.mn.accounts.mapper.AccountsMapper;
import com.mn.accounts.mapper.CustomerMapper;
import com.mn.accounts.repository.AccountsRepository;
import com.mn.accounts.repository.CustomerRepository;
import com.mn.accounts.service.IAccountsService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {
	
	private AccountsRepository accountsRepository;
	private CustomerRepository customerRepository;
	
	
	
	/**
	 * 
	 * @param customerDto - CustomerDto object
	 */
	@Override
	public void createAccount(CustomerDto customerDto) {
		Customer customer=CustomerMapper.mapToCustomer(customerDto, new Customer());
		Optional<Customer> optionalMobileNumber = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
		if(optionalMobileNumber.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    +customerDto.getMobileNumber());
		}
		Customer savedCustomer=customerRepository.save(customer);
		
		Accounts newAccount=createNewAccount(savedCustomer);
		accountsRepository.save(newAccount);
		
		
	}
	
	/**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

	@Override
	public CustomerDto fetchAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
				()->new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
		);
		
		Accounts accounts = accountsRepository.findBycustomerId(customer.getCustomerId()).orElseThrow(
				()->new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
		);
		
		CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		
		return customerDto;
	}

	@Override
	public boolean updateAccount(CustomerDto customerDto) {
		boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
	}

	@Override
	public boolean deleteAccount(String mobileNumber) {
		Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
	}

}

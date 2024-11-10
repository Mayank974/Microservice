package com.mn.accounts.service;

import com.mn.accounts.dto.CustomerDto;

public interface IAccountsService {
	/**
	 * 
	 * @param customerDto - CustomerDto object
	 */
	void createAccount(CustomerDto customerDto);
	
	/**
	 * 
	 * @param mobileNumber - Input Mobile Number
	 * @return Accounts details based on given mobileNumber
	 */
	CustomerDto fetchAccount(String mobileNumber);
	
	 /**
    *
    * @param customerDto - CustomerDto Object
    * @return boolean indicating if the update of Account details is successful or not
    */
	boolean updateAccount(CustomerDto customerDto);
	
	/**
    *
    * @param mobileNumber - Input Mobile Number
    * @return boolean indicating if the delete of Account details is successful or not
    */
   boolean deleteAccount(String mobileNumber);
}

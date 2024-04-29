package com.ashish.service;
import com.ashish.model.UserModel;

	public interface UserService {

		/*
		 * private String userName; private Long mobileNo; private String emailId;
		 * private String address; private int pinCode; private String password;
		 */

		public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode,
				String password);

		public String registerUser(UserModel user);

		public boolean isRegistered(String emailId);

		public String isValidCredential(String emailId, String password);

		public UserModel getUserDetails(String emailId, String password);

		public String getFName(String emailId);

		public String getUserAddr(String userId);

	}


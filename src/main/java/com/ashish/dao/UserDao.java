package com.ashish.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.ashish.model.UserModel;
import com.ashish.service.UserService;
import com.ashish.constant.UserConstant;
import com.ashish.utility.DBUtill;
import com.ashish.utility.MailMessage;

public class UserDao {
	public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode,
			String password) {

		UserModel user = new UserModel(userName, mobileNo, emailId, address, pinCode, password);

		String status = registerUser(user);

		return status;
	}

	public String registerUser(UserModel user) {

		String status = "User Registration Failed!";

		boolean isRegtd = isRegistered(user.getEmail());

		if (isRegtd) {
			status = "Email Id Already Registered!";
			return status;
		}
		Connection conn = DBUtill.getConnection();
		PreparedStatement ps = null;
		if (conn != null) {
			System.out.println("Connected Successfully!");
		}

		try {

			ps = conn.prepareStatement("insert into " + UserConstant.TABLE_USER + " values(?,?,?,?,?,?)");

			ps.setString(1, user.getEmail());
			ps.setString(2, user.getName());
			ps.setLong(3, user.getMobile());
			ps.setString(4, user.getAddress());
			ps.setInt(5, user.getPinCode());
			ps.setString(6, user.getPassword());

			int k = ps.executeUpdate();

			if (k > 0) {
				status = "User Registered Successfully!";
				MailMessage.registrationSuccess(user.getEmail(), user.getName().split(" ")[0]);
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(ps);

		return status;
	}
	public String updateUser(UserModel user) {
		String status = "User updation Failed!";
		Connection conn = DBUtill.getConnection();
		PreparedStatement ps = null;
		
		try {

			ps = conn.prepareStatement("update user set name=?, mobile=? ,address=?, pincode=? where email=?");

			ps.setString(1, user.getName());
			ps.setLong(2, user.getMobile());
			ps.setString(3, user.getAddress());
			ps.setInt(4, user.getPinCode());
			ps.setString(5, user.getEmail());

			int k = ps.executeUpdate();

			if (k > 0) {
				status = "User Update Successfully!";
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(ps);
		return status;
	}

	public boolean isRegistered(String emailId) {
		boolean flag = false;

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from user where email=?");

			ps.setString(1, emailId);

			rs = ps.executeQuery();

			if (rs.next())
				flag = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(rs);

		return flag;
	}

	public String isValidCredential(String emailId, String password) {
		String status = "Login Denied! Incorrect Username or Password";

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			ps = con.prepareStatement("select * from user where email=? and password=?");

			ps.setString(1, emailId);
			ps.setString(2, password);

			rs = ps.executeQuery();

			if (rs.next())
				status = "valid";

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(rs);
		return status;
	}

	public UserModel getUserDetails(String emailId, String password) {

		UserModel user = null;

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from user where email=? and password=?");
			ps.setString(1, emailId);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new UserModel();
				user.setName(rs.getString("name"));
				user.setMobile(rs.getLong("mobile"));
				user.setEmail(rs.getString("email"));
				user.setAddress(rs.getString("address"));
				user.setPinCode(rs.getInt("pincode"));
				user.setPassword(rs.getString("password"));

				return user;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(rs);

		return user;
	}

	public String getFName(String emailId) {
		String fname = "";

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select name from user where email=?");
			ps.setString(1, emailId);

			rs = ps.executeQuery();

			if (rs.next()) {
				fname = rs.getString(1);

				fname = fname.split(" ")[0];

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return fname;
	}

	public String getUserAddr(String userId) {
		String userAddr = "";

		Connection con = DBUtill.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select address from user where email=?");

			ps.setString(1, userId);

			rs = ps.executeQuery();

			if (rs.next())
				userAddr = rs.getString(1);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return userAddr;
	}
	public byte[] getImageByUsername(String email) {
		byte[] image = null;

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select image from user where  email=?");

			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next())
				image = rs.getBytes("image");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(rs);

		return image;
	}
	public String SetImage(InputStream Image,String emailId) {
		String status = "Cannot set image";
		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update user set image = ? where email = ?");
			ps.setBlob(1, Image);
			ps.setString(2, emailId);
			
			int k = ps.executeUpdate();
			if (k > 0) {

				status = "Image Added Successfully";

			} else {

				status = "Image Updation Failed!";
			}
		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}
		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		return status;
	}
	public String removeImage(String email) {
		String status=null;
		Connection con = DBUtill.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement("update user set image = null where email = ?");
			ps.setString(1, email);
			
			int k = ps.executeUpdate();
			if (k > 0) {

				status = "Image Removed Successfully";

			} else {

				status = "Image Updation Failed!";
			}

		} catch (SQLException e) {
			status = "Error: " + e.getMessage();
			e.printStackTrace();
		}
		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		return status;
	}



}

package com.ashish.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ashish.model.DemandModel;
import com.ashish.service.DemandService;
import com.ashish.utility.DBUtill;

public class DemandDao implements DemandService {

	public boolean addProduct(String userId, String prodId, int demandQty) {
		boolean flag = false;

		// get the database connection
		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			// create the prepared statement with the query
			ps = con.prepareStatement("select * from user_demand where username=? and prodid=?");

			ps.setString(1, userId);
			ps.setString(2, prodId);

			rs = ps.executeQuery();

			if (rs.next()) {

				flag = true;
			} else {
				ps2 = con.prepareStatement("insert into  user_demand values(?,?,?)");

				ps2.setString(1, userId);

				ps2.setString(2, prodId);

				ps2.setInt(3, demandQty);

				int k = ps2.executeUpdate();

				if (k > 0)
					flag = true;
			}

		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(ps2);
		DBUtill.closeConnection(rs);
		// return true if the product is added into the db
		return flag;
	}

	public boolean removeProduct(String userId, String prodId) {
		boolean flag = false;

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from user_demand where username=? and prodid=?");

			ps.setString(1, userId);
			ps.setString(2, prodId);

			rs = ps.executeQuery();

			// System.out.println("userId "+userId+"\nprodId: "+prodId);

			if (rs.next()) {

				// System.out.println("userId "+userId+"\nprodId: "+prodId);
				ps2 = con.prepareStatement("delete from  user_demand where username=? and prodid=?");

				ps2.setString(1, userId);

				ps2.setString(2, prodId);

				int k = ps2.executeUpdate();

				if (k > 0)
					flag = true;

			} else {
				flag = true;
			}

		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		}

		DBUtill.closeConnection(con);
		DBUtill.closeConnection(ps);
		DBUtill.closeConnection(ps2);
		DBUtill.closeConnection(rs);

		return flag;
	}

	public boolean addProduct(DemandModel userDemandBean) {

		return addProduct(userDemandBean.getUserName(), userDemandBean.getProdId(), userDemandBean.getDemandQty());
	}

	public List<DemandModel> haveDemanded(String prodId) {
		List<DemandModel> demandList = new ArrayList<DemandModel>();

		Connection con = DBUtill.getConnection();

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = con.prepareStatement("select * from user_demand where prodid=?");
			ps.setString(1, prodId);
			rs = ps.executeQuery();

			while (rs.next()) {

				DemandModel demand = new DemandModel(rs.getString("username"), rs.getString("prodid"),
						rs.getInt("quantity"));

				demandList.add(demand);

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return demandList;
	}

}

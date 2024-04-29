package com.ashish.service;

import java.util.List;

import com.ashish.model.OrderDetails;
import com.ashish.model.OrderModel;
import com.ashish.model.TransactionModel;



public interface OrderService {

	public String paymentSuccess(String userName, double paidAmount);

	public boolean addOrder(OrderModel order);

	public boolean addTransaction(TransactionModel transaction);

	public int countSoldItem(String prodId);

	public List<OrderModel> getAllOrders();

	public List<OrderModel> getOrdersByUserId(String emailId);

	public List<OrderDetails> getAllOrderDetails(String userEmailId);

	public String shipNow(String orderId, String prodId);
}

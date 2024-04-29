package com.ashish.service;

import java.util.List;

import com.ashish.model.DemandModel;


public interface DemandService {

	public boolean addProduct(String userId, String prodId, int demandQty);

	public boolean addProduct(DemandModel userDemandBean);

	public boolean removeProduct(String userId, String prodId);

	public List<DemandModel> haveDemanded(String prodId);

}

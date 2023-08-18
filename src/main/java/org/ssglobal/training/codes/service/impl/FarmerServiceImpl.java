package org.ssglobal.training.codes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.repository.FarmerRepository;
import org.ssglobal.training.codes.service.FarmerService;

@Service
public class FarmerServiceImpl implements FarmerService {
	
	@Autowired
	private FarmerRepository repository;
	
	@Override
	public List<FarmerComplaint> selectFarmerComplaints(Integer farmerId) {
		return repository.selectFarmerComplaints(farmerId);
	}
	
	@Override
	public FarmerComplaint insertIntoFarmerComplaint(Map<String, Object> payload) {
		return repository.insertIntoFarmerComplaint(payload);
	}
}

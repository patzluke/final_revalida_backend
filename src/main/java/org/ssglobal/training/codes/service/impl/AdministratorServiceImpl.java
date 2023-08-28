package org.ssglobal.training.codes.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.UserApplicants;
import org.ssglobal.training.codes.repository.AdministratorRepository;
import org.ssglobal.training.codes.service.AdministratorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
	
	@Autowired
	private AdministratorRepository repository;
	
	@Override
	public List<Administrator> selectAllAdministrators() {
		return repository.findAllAdministrators();
	}
	
	//User Applicants
	@Override
	public List<UserApplicants> selectAllUserApplicants() {
		return repository.selectAllUserApplicants();
	}	
	
	//Farmers
	@Override
	public List<Farmer> selectAllFarmers() {
		return repository.selectAllFarmers();
	}
	
	//Suppliers
	@Override
	public List<Supplier> selectAllSuppliers() {
		return repository.selectAllSuppliers();
	}
	
	//FarmingTips
	@Override
	public List<FarmingTip> selectAllFarmingTip() {
		return repository.selectAllFarmingTip();
	}
	
	@Override
	public FarmingTip insertIntoFarmingTip(FarmingTip farmingTip) {
		farmingTip.setFarmingTipId(null);
		farmingTip.setDateCreated(LocalDateTime.now());
		return repository.insertIntoFarmingTip(farmingTip);
	}
	
	@Override
	public FarmingTip updateIntoFarmingTip(FarmingTip farmingTip) {
		farmingTip.setDateModified(LocalDateTime.now());
		return repository.updateIntoFarmingTip(farmingTip);
	}
	
	@Override
	public FarmingTip deleteFarmingTip(Integer farmingTipId) {
		return repository.deleteFarmingTip(farmingTipId);
	}

	//Farmer Complaints
	@Override
	public List<FarmerComplaint> selectAllFarmerComplaints() {
		return repository.selectAllFarmerComplaints();
	}

	@Override
	public FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint) {
		return repository.updateIntoFarmerComplaint(farmerComplaint);
	}
	
	@Override
	public Object validateUserAccount(Map<String, Object> payload) {
		return repository.validateUserAccount(payload);
	}
}

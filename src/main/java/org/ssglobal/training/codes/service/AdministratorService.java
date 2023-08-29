package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ssglobal.training.codes.models.Administrator;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.FarmingTip;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.UserApplicants;

public interface AdministratorService {
	
	List<Administrator> selectAllAdministrators();
	Optional<Administrator> findOneByUserId(Integer userId);
	Administrator updateAdminInfo(Map<String, Object> payload);
	
	//User Applicants
	List<UserApplicants> selectAllUserApplicants();
		
	//Farmers
	List<Farmer> selectAllFarmers();
	
	//Suppliers
	List<Supplier> selectAllSuppliers();
	
	//Farming Tips
	List<FarmingTip> selectAllFarmingTip();
	FarmingTip insertIntoFarmingTip(FarmingTip farmingTip);
	FarmingTip updateIntoFarmingTip(FarmingTip farmingTip);
	FarmingTip deleteFarmingTip(Integer farmingTipId);
	
	//Farmer Complaints
	List<FarmerComplaint> selectAllFarmerComplaints();
	FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint);
	
	Object validateUserAccount(Map<String, Object> payload);
}

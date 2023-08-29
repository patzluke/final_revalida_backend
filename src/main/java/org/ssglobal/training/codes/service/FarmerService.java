package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;

public interface FarmerService {
	
	Optional<Farmer> findOneByUserId(Integer userId);
	Farmer updateFarmerInfo(Map<String, Object> payload);
	
	List<FarmerComplaint> selectFarmerComplaints(Integer farmerId);
	FarmerComplaint insertIntoFarmerComplaint(Map<String, Object> payload);
	FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint);
	FarmerComplaint softDeleteFarmerComplaint(Integer farmingTipId);
	
	//Post Advertisement
	List<PostAdvertisement> selectAllPostAdvertisements();
	
	//Post Advertisemet Response
	PostAdvertisementResponse insertIntoPostAdvertisementResponse(Map<String, Object> payload);
}

package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.models.FarmerComplaint;

public interface FarmerService {
	
	List<FarmerComplaint> selectFarmerComplaints(Integer farmerId);
}

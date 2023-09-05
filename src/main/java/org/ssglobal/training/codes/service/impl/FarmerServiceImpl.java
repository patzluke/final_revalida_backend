package org.ssglobal.training.codes.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.CourseEnrolled;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.repository.FarmerRepository;
import org.ssglobal.training.codes.service.FarmerService;

@Service
public class FarmerServiceImpl implements FarmerService {
	
	@Autowired
	private FarmerRepository repository;
	
	@Override
	public Optional<Farmer> findOneByUserId(Integer userId) {
		return repository.findOneByUserId(userId);
	}
	
	@Override
	public Farmer updateFarmerInfo(Map<String, Object> payload) {
		return repository.updateFarmerInfo(payload);
	}
	
	@Override
	public List<FarmerComplaint> selectFarmerComplaints(Integer farmerId) {
		return repository.selectFarmerComplaints(farmerId);
	}
	
	@Override
	public FarmerComplaint insertIntoFarmerComplaint(Map<String, Object> payload) {
		return repository.insertIntoFarmerComplaint(payload);
	}
	
	@Override
	public FarmerComplaint updateIntoFarmerComplaint(FarmerComplaint farmerComplaint) {
		return repository.updateIntoFarmerComplaint(farmerComplaint);
	}
	
	@Override
	public FarmerComplaint softDeleteFarmerComplaint(Integer farmingTipId) {
		return repository.softDeleteFarmerComplaint(farmingTipId);
	}
	
	@Override
	public List<PostAdvertisement> selectAllPostAdvertisements() {
		return repository.selectAllPostAdvertisements();
	}
	
	@Override
	public PostAdvertisementResponse insertIntoPostAdvertisementResponse(Map<String, Object> payload) {
		return repository.insertIntoPostAdvertisementResponse(payload);
	}
	
	@Override
	public List<Course> selectAllCourses() {
		return repository.selectAllCourses();
	}

	@Override
	public List<CourseEnrolled> selectAllCoursesEnrolledByFarmer(Integer farmerId) {
		return repository.selectAllCoursesEnrolledByFarmer(farmerId);
	}

	@Override
	public CourseEnrolled insertIntoCourseEnrolled(Map<String, Object> payload) {
		return repository.insertIntoCourseEnrolled(payload);
	}
	
	@Override
	public List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByFarmerId(Integer farmerId) {
		return repository.selectAllPostAdvertisementResponsesByFarmerId(farmerId);
	}
	
	@Override
	public List<CropPayment> selectAllCropPaymentByFarmer(Integer farmerId) {
		return repository.selectAllCropPaymentByFarmer(farmerId);
	}
	
	@Override
	public CropPayment insertIntoSellCropDetailsAndCropOrdersAndPayment(Map<String, Object> payload) {
		return repository.insertIntoSellCropDetailsAndCropOrdersAndPayment(payload);
	}

	@Override
	public double calculateTotalSales(Integer farmerId) {
	    try {
	        return repository.calculateTotalSales(farmerId);
	    } catch (Exception e) {
	        // Handle the exception or rethrow it as a custom exception
	        // Log the error, perform error handling, or return a default value, depending on your requirements
	        e.printStackTrace(); // or use a logging framework
	        return 0.0; // Default value or error indicator
	    }
	}

}

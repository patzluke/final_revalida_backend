package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ssglobal.training.codes.models.Course;
import org.ssglobal.training.codes.models.CourseEnrolled;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.Farmer;
import org.ssglobal.training.codes.models.FarmerComplaint;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.SellCropDetail;

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
	
	//course
	List<Course> selectAllCourses();
	
	//course enrolled
	List<CourseEnrolled> selectAllCoursesEnrolledByFarmer(Integer farmerId);
	CourseEnrolled insertIntoCourseEnrolled(Map<String, Object> payload);
	
	List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByFarmerId(Integer farmerId);
	
	//Sell Crop Payment
	List<CropPayment> selectAllCropPaymentByFarmer(Integer farmerId);
	CropPayment insertIntoSellCropDetailsAndCropOrdersAndPayment(Map<String, Object> payload);
	
	// Sales Repost
	double calculateTotalSales(Integer farmerId);
	List<Integer> getSalesDataPerMonth(Integer farmerId);
	int countCropOrdersPerYear(Integer farmerId);
	List<CropPayment> getTopThreeRecentSellCropDetails(Integer farmerId);
}

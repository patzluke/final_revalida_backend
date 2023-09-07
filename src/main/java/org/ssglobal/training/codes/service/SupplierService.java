package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.SellCropDetail;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.SupplierComplaint;

public interface SupplierService {

	Supplier updateSupplierInfo(Map<String, Object> payload);
	Optional<Supplier> findOneByUserId(Integer userId);
	
	//Post Advertisement
	List<PostAdvertisement> selectPostAdvertisementBySupplierId(Integer supplierId);
	PostAdvertisement insertIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement updateIntoPostAdvertisement(Map<String, Object> payload);
	PostAdvertisement softDeletePostAdvertisement(Integer postId);
	
	//Crop Specialization
	List<CropSpecialization> selectAllCropSpecialization();
	
	// Post Advertisement Respones
	List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByPostId(Integer postId);
	PostAdvertisementResponse updatePostAdvertisementResponsesIsAcceptedStatus(Map<String, Object> payload);
	
	//Crop Payment
	List<CropPayment> selectAllCropPaymentBySupplier(Integer supplierId);
	CropPayment updateCropPaymentStatus(Map<String, Object> payload);

//	List<SellCropDetail> getSellCropDetailByFarmerId(Integer farmerId);
	List<SellCropDetail> getSellCropDetailByFarmerId();
	
	//Sell Crop Details
	List<SellCropDetail> selectAllSellCropDetails();
	
	CropPayment updateCropOrderStatus(Map<String, Object> payload);
	
	//Supplier Complaint
	List<SupplierComplaint> selectSupplierComplaints(Integer supplierId);
	SupplierComplaint insertIntoSupplierComplaint(Map<String, Object> payload);
	SupplierComplaint updateIntoSupplierComplaint(SupplierComplaint supplierComplaint);
	SupplierComplaint softDeleteSupplierComplaint(Integer supplierComplaintId);
}

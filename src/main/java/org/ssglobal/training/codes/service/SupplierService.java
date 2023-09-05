package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.Supplier;

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
}

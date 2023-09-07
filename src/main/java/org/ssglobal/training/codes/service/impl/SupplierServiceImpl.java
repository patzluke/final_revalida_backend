package org.ssglobal.training.codes.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.CropPayment;
import org.ssglobal.training.codes.models.CropSpecialization;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.models.PostAdvertisementResponse;
import org.ssglobal.training.codes.models.SellCropDetail;
import org.ssglobal.training.codes.models.Supplier;
import org.ssglobal.training.codes.models.SupplierComplaint;
import org.ssglobal.training.codes.repository.SupplierRepository;
import org.ssglobal.training.codes.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository repository;
	
	@Override
	public Supplier updateSupplierInfo(Map<String, Object> payload) {
		return repository.updateSupplierInfo(payload);
	}
	
	@Override
	public Optional<Supplier> findOneByUserId(Integer userId) {
		return repository.findOneByUserId(userId);
	}
	
	//Post Advertisement
	@Override
	public List<PostAdvertisement> selectPostAdvertisementBySupplierId(Integer supplierId) {
		return repository.selectPostAdvertisementBySupplierId(supplierId);
	}

	@Override
	public PostAdvertisement insertIntoPostAdvertisement(Map<String, Object> payload) {
		return repository.insertIntoPostAdvertisement(payload);
	}

	@Override
	public PostAdvertisement updateIntoPostAdvertisement(Map<String, Object> payload) {
		return repository.updateIntoPostAdvertisement(payload);
	}

	@Override
	public PostAdvertisement softDeletePostAdvertisement(Integer postId) {
		return repository.softDeletePostAdvertisement(postId);
	}
	
	//Crop Specialization
	@Override
	public List<CropSpecialization> selectAllCropSpecialization() {
		return repository.selectAllCropSpecialization();
	}
	
	// Post Advertisement Respones
	@Override
	public List<PostAdvertisementResponse> selectAllPostAdvertisementResponsesByPostId(Integer postId) {
		return repository.selectAllPostAdvertisementResponsesByPostId(postId);
	}
	
	@Override
	public PostAdvertisementResponse updatePostAdvertisementResponsesIsAcceptedStatus(Map<String, Object> payload) {
		repository.insertIntoUserNotifications(payload);
		return repository.updatePostAdvertisementResponsesIsAcceptedStatus(payload);
	}
	
	@Override
	public List<CropPayment> selectAllCropPaymentBySupplier(Integer supplierId) {
		return repository.selectAllCropPaymentBySupplier(supplierId);
	}
	
	@Override
	public CropPayment updateCropPaymentStatus(Map<String, Object> payload) {
		repository.insertIntoUserNotificationsSubmitProofOfPayment(payload);
		return repository.updateCropPaymentStatus(payload);
	}

	@Override
	public List<SellCropDetail> getSellCropDetailByFarmerId() {
		return repository.getSellCropDetailByFarmerId();
	}

	
	@Override
	public List<SellCropDetail> selectAllSellCropDetails() {
		return repository.selectAllSellCropDetails();
	}
	
	@Override
	public CropPayment updateCropOrderStatus(Map<String, Object> payload) {
		return repository.updateCropOrderStatus(payload);
	}

	// Supplier complaint
	@Override
	public List<SupplierComplaint> selectSupplierComplaints(Integer supplierId) {
		return repository.selectSupplierComplaints(supplierId);
	}

	@Override
	public SupplierComplaint insertIntoSupplierComplaint(Map<String, Object> payload) {
		return repository.insertIntoSupplierComplaint(payload);
	}

	@Override
	public SupplierComplaint updateIntoSupplierComplaint(SupplierComplaint supplierComplaint) {
		return repository.updateIntoSupplierComplaint(supplierComplaint);
	}

	@Override
	public SupplierComplaint softDeleteSupplierComplaint(Integer supplierComplaintId) {
		return repository.softDeleteSupplierComplaint(supplierComplaintId);
	}
}

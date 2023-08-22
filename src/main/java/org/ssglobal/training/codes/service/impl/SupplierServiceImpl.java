package org.ssglobal.training.codes.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.models.PostAdvertisement;
import org.ssglobal.training.codes.repository.SupplierRepository;
import org.ssglobal.training.codes.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierRepository repository;
	
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
}

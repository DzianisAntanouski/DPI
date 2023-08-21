package com.sap.showcase.media.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.showcase.media.model.Advertisement;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Long>{	
	public List<Advertisement> findByMagazineMagazineID(Long magazineID);
}
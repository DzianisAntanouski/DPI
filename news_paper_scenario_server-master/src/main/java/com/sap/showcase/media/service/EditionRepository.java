package com.sap.showcase.media.service;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sap.showcase.media.model.Edition;

public interface EditionRepository extends CrudRepository<Edition, Long>{
	public List<Edition> findByMagazineMagazineID(Long magazineID);
}

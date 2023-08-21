package com.sap.showcase.media.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.showcase.media.model.Magazine;

@Service
public class MagazineService {
	@Autowired
	private MagazineRepository magazineRepository;

	public List<Magazine> getMagazines(){
		List<Magazine> magazines = new ArrayList<>();
		magazineRepository.findAll().forEach(magazines::add);
		return magazines;
	}
	
	public Magazine getMagazine(Long id) {
		return magazineRepository.findById(id).get();
		
	}

	public void addMagazine(Magazine magazine) {
		magazineRepository.save(magazine);		
	}

	public void updateMagazine(Long id, Magazine magazine) {
		magazineRepository.save(magazine);		
	}

	public void deleteMagazine(Long id) {
		magazineRepository.deleteById(id);
	}
	public void deleteAllMagazines() {
		magazineRepository.deleteAll();
	}
	public boolean exists(Long id) {
		return magazineRepository.existsById(id);
	}
	public boolean existAny() {
		if(magazineRepository.count()>0)
			return true;
		else{
			return false;			
		}		
	}
}

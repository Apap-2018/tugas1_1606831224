package com.apap.tugas1new.service;

import java.util.List;

import com.apap.tugas1new.model.ProvinsiModel;

public interface ProvinsiService {
	
	ProvinsiModel findById(Long id);
	List<ProvinsiModel> findAll();
}

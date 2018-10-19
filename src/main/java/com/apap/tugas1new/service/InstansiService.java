package com.apap.tugas1new.service;

import java.util.List;

import com.apap.tugas1new.model.InstansiModel;

public interface InstansiService {
	
	List<InstansiModel> findAll();
	
	InstansiModel findById(Long id);

}

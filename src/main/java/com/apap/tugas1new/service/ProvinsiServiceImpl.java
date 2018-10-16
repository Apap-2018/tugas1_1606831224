package com.apap.tugas1new.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.ProvinsiModel;
import com.apap.tugas1new.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{

	@Autowired
	private ProvinsiDB provinsiDb;

	@Override
	public ProvinsiModel findById(Long id) {
		// TODO Auto-generated method stub
		
		return provinsiDb.findById(id).get();
	}

	@Override
	public List<ProvinsiModel> findAll() {
		// TODO Auto-generated method stub
		return provinsiDb.findAll();
	}


	

}

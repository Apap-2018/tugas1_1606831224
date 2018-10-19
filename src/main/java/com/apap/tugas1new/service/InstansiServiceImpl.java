package com.apap.tugas1new.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.InstansiModel;
import com.apap.tugas1new.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{

	@Autowired
	private InstansiDB instansiDb;

	@Override
	public List<InstansiModel> findAll() {
		// TODO Auto-generated method stub
		return instansiDb.findAll();
	}

	@Override
	public InstansiModel findById(Long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id).get();
	}
}

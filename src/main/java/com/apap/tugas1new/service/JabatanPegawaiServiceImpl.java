package com.apap.tugas1new.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.JabatanPegawaiModel;
import com.apap.tugas1new.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanPegawaiServiceImpl implements JabatanPegawaiService{

	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;

	@Override
	public void add(JabatanPegawaiModel jabatanPegawai) {
		// TODO Auto-generated method stub
		jabatanPegawaiDb.save(jabatanPegawai);
		
	}
}

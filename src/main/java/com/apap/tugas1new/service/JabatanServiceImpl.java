package com.apap.tugas1new.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.JabatanModel;
import com.apap.tugas1new.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService{
	
	@Autowired
	private JabatanDB jabatanDb;

	@Override
	public List<JabatanModel> findAll() {
		// TODO Auto-generated method stub
		return jabatanDb.findAll();
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}

	@Override
	public JabatanModel findById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id).get();
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		
		jabatanDb.delete(jabatan);
		
	}

}

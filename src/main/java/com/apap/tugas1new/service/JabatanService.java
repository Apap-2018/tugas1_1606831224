package com.apap.tugas1new.service;

import java.util.List;

import com.apap.tugas1new.model.JabatanModel;

public interface JabatanService {
	
	List<JabatanModel> findAll();
	
	void addJabatan(JabatanModel jabatan);
	
	JabatanModel findById(Long id);
	
	void deleteJabatan(JabatanModel jabatan);

}

package com.apap.tugas1new.service;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1new.model.InstansiModel;
import com.apap.tugas1new.model.JabatanModel;
import com.apap.tugas1new.model.PegawaiModel;
import com.apap.tugas1new.model.ProvinsiModel;

public interface PegawaiService{
	
	void add(PegawaiModel pegawai);
	
	PegawaiModel findPegawaiByNip(String nip);
	
	int hitungGajibyNIP(String nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	String makeNip(InstansiModel instansi, PegawaiModel pegawai);
	
	void delete(PegawaiModel pegawai);
	
	void update(PegawaiModel pegawaiUpdate, PegawaiModel pegawaiBefore);
	
	List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	
	List<PegawaiModel> findAll();
	
}

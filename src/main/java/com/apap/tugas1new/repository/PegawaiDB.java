package com.apap.tugas1new.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1new.model.InstansiModel;
import com.apap.tugas1new.model.PegawaiModel;


@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long>{

	PegawaiModel findByNip(String nip);
	
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	
	
	List<PegawaiModel> findByTanggalLahirAndTahunMasukAndInstansi(Date tanggalLahir, String tahunMasuk, InstansiModel instansi);
	
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
}

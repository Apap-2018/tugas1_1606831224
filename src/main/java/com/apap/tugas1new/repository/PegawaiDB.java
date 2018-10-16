package com.apap.tugas1new.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1new.model.PegawaiModel;


@Repository
public interface PegawaiDB extends JpaRepository<PegawaiModel, Long>{

	PegawaiModel findByNip(String nip);
}

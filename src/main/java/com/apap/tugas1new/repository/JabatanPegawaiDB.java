package com.apap.tugas1new.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1new.model.JabatanPegawaiModel;


@Repository
public interface JabatanPegawaiDB extends JpaRepository<JabatanPegawaiModel, Long> {

}

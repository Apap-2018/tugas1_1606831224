package com.apap.tugas1new.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1new.model.JabatanModel;


@Repository
public interface JabatanDB extends JpaRepository<JabatanModel, Long> {

}

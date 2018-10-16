package com.apap.tugas1new.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1new.model.InstansiModel;


@Repository
public interface InstansiDB  extends JpaRepository<InstansiModel, Long> {

}

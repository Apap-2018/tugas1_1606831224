package com.apap.tugas1new.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{

	@Autowired
	private InstansiDB instansiDb;
}

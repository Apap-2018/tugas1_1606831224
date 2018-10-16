package com.apap.tugas1new.service;

import com.apap.tugas1new.model.PegawaiModel;

public interface PegawaiService{
	
	void addPilot(PegawaiModel pilot);
	
	PegawaiModel findPegawaiByNip(String nip);
	
	int hitungGajibyNIP(String nip);
	
}

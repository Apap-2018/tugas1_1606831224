package com.apap.tugas1new.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.JabatanPegawaiModel;
import com.apap.tugas1new.model.PegawaiModel;
import com.apap.tugas1new.model.ProvinsiModel;
import com.apap.tugas1new.repository.PegawaiDB;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDB pegawaiDb;
	
	@Override
	public void addPilot(PegawaiModel pilot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PegawaiModel findPegawaiByNip(String nip) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public int hitungGajibyNIP(String nip) {
		// TODO Auto-generated method stub
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		int banyakJabatan = listJabatan.size();
		double gajiPokok = 0;
		
		for (int i = 0; i < banyakJabatan; i++) {
			double gajiJabatan = listJabatan.get(i).getJabatan().getGajiPokok();
			if (gajiPokok < gajiJabatan) {
				gajiPokok = gajiJabatan;
			}
		}
		
		double presentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		System.out.println();
		System.out.println(gajiPokok);
		System.out.println(presentaseTunjangan);
		gajiPokok += (presentaseTunjangan*gajiPokok)/100;
		
		System.out.println();
		System.out.println(gajiPokok);
		System.out.println();
		
		return (int) gajiPokok;
	}


	
	

}

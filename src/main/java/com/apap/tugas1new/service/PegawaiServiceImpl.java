package com.apap.tugas1new.service;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.InstansiModel;
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
	public void add(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.save(pegawai);
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

	@Override
	public List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
	}

	@Override
	public String makeNip(InstansiModel instansi, PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		
		ProvinsiModel provinsi = instansi.getProvinsi();
		
		String nip = "";
		nip += instansi.getId();

		Date tanggalLahir = pegawai.getTanggalLahir();
		String[] tglLahir = (""+tanggalLahir).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		
		nip += pegawai.getTahunMasuk();
		
		List<PegawaiModel> listPegawai = pegawaiDb.findByTanggalLahirAndTahunMasukAndInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getInstansi());
		
		int banyakPegawai = listPegawai.size();
		
		if (banyakPegawai >= 10) {
			nip += banyakPegawai;
		}
		else {
			nip += "0" + (banyakPegawai+1);
		}
		
		
		return nip;
	}




	
	

}

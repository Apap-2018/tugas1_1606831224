package com.apap.tugas1new.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1new.model.InstansiModel;
import com.apap.tugas1new.model.JabatanModel;
import com.apap.tugas1new.model.JabatanPegawaiModel;
import com.apap.tugas1new.model.PegawaiModel;
import com.apap.tugas1new.model.ProvinsiModel;
import com.apap.tugas1new.repository.JabatanPegawaiDB;
import com.apap.tugas1new.repository.PegawaiDB;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDB pegawaiDb;

	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;
	
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

	@Override
	public void delete(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		pegawaiDb.delete(pegawai);
	}

	@Override
	public void update(PegawaiModel pegawaiUpdate, PegawaiModel pegawaiBefore) {
		// TODO Auto-generated method stub
		pegawaiBefore.setInstansi(pegawaiUpdate.getInstansi());
		pegawaiBefore.setNama(pegawaiUpdate.getNama());
		pegawaiBefore.setNip(pegawaiUpdate.getNip());
		pegawaiBefore.setTahunMasuk(pegawaiUpdate.getTahunMasuk());
		pegawaiBefore.setTanggalLahir(pegawaiUpdate.getTanggalLahir());
		pegawaiBefore.setTempatLahir(pegawaiUpdate.getTempatLahir());
		
		
		// update jabatan
		int jumlahList = pegawaiBefore.getJabatanPegawaiList().size();
		for (int i = 0; i< jumlahList; i++) {
			pegawaiBefore.getJabatanPegawaiList().get(i).setJabatan(pegawaiUpdate.getJabatanPegawaiList().get(i).getJabatan());
		}
		
		for (int i = jumlahList; i < pegawaiUpdate.getJabatanPegawaiList().size(); i++) {
			pegawaiUpdate.getJabatanPegawaiList().get(i).setPegawai(pegawaiBefore);
			jabatanPegawaiDb.save(pegawaiUpdate.getJabatanPegawaiList().get(i));
		}
		
	}

	@Override
	public List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
		List<PegawaiModel> listPegawaiInstansi = pegawaiDb.findByInstansi(instansi);
		
		for (int i = 0; i < listPegawaiInstansi.size(); i++) {
			int sizeJ = listPegawaiInstansi.get(i).getJabatanPegawaiList().size();
			for (int j = 0; j < sizeJ; j++ ) {
				if (listPegawaiInstansi.get(i).getJabatanPegawaiList().get(j).getJabatan().getId() == jabatan.getId()) {
					hasil.add(listPegawaiInstansi.get(i));
				}
			}
			
		}
		return hasil;
	}

	@Override
	public List<PegawaiModel> findByInstansi(InstansiModel instansi) {
		// TODO Auto-generated method stub
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public List<PegawaiModel> findAll() {
		// TODO Auto-generated method stub
		return pegawaiDb.findAll();
	}




	
	

}

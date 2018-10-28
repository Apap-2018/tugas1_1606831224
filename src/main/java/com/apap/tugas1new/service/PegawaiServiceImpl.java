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
import com.apap.tugas1new.repository.JabatanDB;
import com.apap.tugas1new.repository.JabatanPegawaiDB;
import com.apap.tugas1new.repository.PegawaiDB;


@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{

	@Autowired
	private PegawaiDB pegawaiDb;

	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;
	
	@Autowired
	private JabatanDB jabatanDb;
	
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
		
		System.out.println(this.checkUpdate(pegawaiUpdate, pegawaiBefore));
		
		if (this.checkUpdate(pegawaiUpdate, pegawaiBefore)) {
			String nip = this.makeNip(pegawaiUpdate.getInstansi(), pegawaiUpdate);
			pegawaiBefore.setNip(nip);
		}
		
		pegawaiBefore.setInstansi(pegawaiUpdate.getInstansi());
		pegawaiBefore.setNama(pegawaiUpdate.getNama());
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
	public boolean checkUpdate(PegawaiModel pegawaiUpdate, PegawaiModel pegawaiBefore) {
		if (pegawaiUpdate.getTahunMasuk().equals(pegawaiBefore.getTahunMasuk())) {
			if (pegawaiUpdate.getTanggalLahir().equals(pegawaiBefore.getTanggalLahir())) {
				if (pegawaiUpdate.getInstansi().getId() == pegawaiBefore.getInstansi().getId()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<PegawaiModel> findByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
		List<JabatanPegawaiModel> listPegawaiJabatan = jabatanDb.findById(jabatan.getId()).get().getJabatanPegawaiList();
		
		for (int i = 0; i < listPegawaiJabatan.size(); i++) {
			if (listPegawaiJabatan.get(i).getPegawai().getInstansi().getId() == instansi.getId()) {
				hasil.add(listPegawaiJabatan.get(i).getPegawai());
			}
			
		}
		
		return hasil;
	}
	
	@Override
	public List<PegawaiModel> findByProvinsiAndJabatan(ProvinsiModel provinsi, JabatanModel jabatan) {
		// TODO Auto-generated method stub
		List<PegawaiModel> hasil = new ArrayList<PegawaiModel>();
		List<JabatanPegawaiModel> listPegawaiJabatan = jabatanDb.findById(jabatan.getId()).get().getJabatanPegawaiList();
		
		for (int i = 0; i < listPegawaiJabatan.size(); i++) {
			if (listPegawaiJabatan.get(i).getPegawai().getInstansi().getProvinsi().getId() == provinsi.getId()) {
				hasil.add(listPegawaiJabatan.get(i).getPegawai());
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

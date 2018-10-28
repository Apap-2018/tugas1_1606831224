package com.apap.tugas1new.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1new.model.InstansiModel;
import com.apap.tugas1new.model.JabatanModel;
import com.apap.tugas1new.model.JabatanPegawaiModel;
import com.apap.tugas1new.model.PegawaiModel;
import com.apap.tugas1new.model.ProvinsiModel;
import com.apap.tugas1new.service.InstansiService;
import com.apap.tugas1new.service.JabatanPegawaiService;
import com.apap.tugas1new.service.JabatanService;
import com.apap.tugas1new.service.PegawaiService;
import com.apap.tugas1new.service.ProvinsiService;



@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired 
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	
	@RequestMapping("/")
	public String home(Model model) {
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		List<InstansiModel> listInstansi = instansiService.findAll();
		
		model.addAttribute("title", "Home Page");
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String getPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();

		
		model.addAttribute("title", "Detail Pegawai");
		model.addAttribute("gaji", pegawaiService.hitungGajibyNIP(nip));
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		return "detail-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setTanggalLahir(new Date(118, 9, 11));
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		List<JabatanPegawaiModel> listJabatanPegawai = new ArrayList<JabatanPegawaiModel>();
		pegawai.setJabatanPegawaiList(listJabatanPegawai);
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		List<InstansiModel> listInstansi = listProvinsi.get(0).getInstansiList();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan"})
	public String tambahJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getInstansiList();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String tambahJabatanBaru(@ModelAttribute PegawaiModel pegawai, Model model) {
		
		// membuat nip
		String nip = pegawaiService.makeNip(pegawai.getInstansi(), pegawai);
		pegawai.setNip(nip);
		
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();
		
		pegawai.setJabatanPegawaiList(new ArrayList<JabatanPegawaiModel>());
		
		// tambahkan pegawai pada db dan megeset instansi
		pegawaiService.add(pegawai);
		
		// menambahkan setiap jabatanPegawai pada list
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.add(listJabatan.get(i));
		}
		
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "tambah-pegawai-sukses";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = pegawai.getInstansi().getProvinsi().getInstansiList();
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addJabatan"})
	public String ubahJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = pegawai_new;
		
		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawaiList().add(jabatanPegawai);
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getInstansiList();

		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		
		model.addAttribute("title", "Ubah Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("pegawai", pegawai);
		return "ubah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String ubahJabatanBaru(@ModelAttribute PegawaiModel pegawai, Model model) {
		// ambil pegawai sebelum diupdate
		PegawaiModel pegawaiBefore = pegawaiService.findPegawaiByNip(pegawai.getNip());
		
		System.out.println("hahahahahha " + pegawai.getJabatanPegawaiList());
		
		pegawaiService.update(pegawai, pegawaiBefore);
		
		String nip = pegawaiBefore.getNip();
		
		model.addAttribute("title", "Sukses");
		model.addAttribute("nipPegawai", nip);
		return "ubah-pegawai-sukses";
	}
	
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(Model model) {
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getInstansiList();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Cari Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);

		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params= {"search"})
	public String cariPegawaiBaru(@RequestParam(value="IdProvinsi", required=false) Optional<Long> IdProvinsi, 
			@RequestParam(value="IdInstansi", required=false) Optional<Long> IdInstansi,
			@RequestParam(value="IdJabatan", required=false) Optional<Long> IdJabatan, Model model) {
		
	
		
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		if (IdProvinsi.isPresent()) {
			if (IdInstansi.isPresent()) {
				if (IdJabatan.isPresent()) {
					InstansiModel instansi = instansiService.findById(IdInstansi.get());
					JabatanModel jabatan = jabatanService.findById(IdJabatan.get());
					
					listPegawai = pegawaiService.findByInstansiAndJabatan(instansi, jabatan);
					
				}
				else {
					InstansiModel instansi = instansiService.findById(IdInstansi.get());
					listPegawai = pegawaiService.findByInstansi(instansi);
				}
				
			}
			else {
				if (IdJabatan.isPresent()) {
					ProvinsiModel provinsi = provinsiService.findById(IdProvinsi.get());
					JabatanModel jabatan = jabatanService.findById(IdJabatan.get());
					
					listPegawai = pegawaiService.findByProvinsiAndJabatan(provinsi, jabatan);

					
				}
				else {
					List<InstansiModel> listInstansi = provinsiService.findById(IdProvinsi.get()).getInstansiList();
					for (int i = 0; i < listInstansi.size(); i++) {
						List<PegawaiModel> listPegawaiBaru = listInstansi.get(i).getPegawaiList();
						listPegawai.addAll(listPegawaiBaru);
					}
				}
			}
		}
		else {
			if (IdJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.findById(IdJabatan.get());
				for (int i = 0; i< jabatan.getJabatanPegawaiList().size(); i++) {
					listPegawai.add(jabatan.getJabatanPegawaiList().get(i).getPegawai());
				}
				
			}
		}
		
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = new ArrayList<InstansiModel>();
		listInstansi = listProvinsi.get(0).getInstansiList();
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("title", "Cari Pegawai");
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listPegawai", listPegawai);
		return "cari-pegawai";
	}
	
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	public String lihatPegawaiTuaDanMuda(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		InstansiModel instansi = instansiService.findById(idInstansi);
		
		List<PegawaiModel> listPegawai =  pegawaiService.findByInstansiOrderByTanggalLahirAsc(instansi);
		
		if (!listPegawai.isEmpty()) {
			int banyakPegawai = listPegawai.size();
			
			PegawaiModel pegawai_muda = listPegawai.get(banyakPegawai-1);
			PegawaiModel pegawai_tua = listPegawai.get(0);

			
			model.addAttribute("gajiMuda", pegawaiService.hitungGajibyNIP(pegawai_muda.getNip()));
			model.addAttribute("gajiTua", pegawaiService.hitungGajibyNIP(pegawai_tua.getNip()));
			
			model.addAttribute("pegawaiMuda", pegawai_muda);
			model.addAttribute("pegawaiTua", pegawai_tua);
			
		}

		model.addAttribute("title", "Detail Pegawai");
		return "termuda-tertua-pegawai";
	}
	
	

}

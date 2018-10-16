package com.apap.tugas1new.controller;

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
		
		model.addAttribute("title", "Home Page");
		model.addAttribute("listJabatan", listJabatan);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	public String getPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.findPegawaiByNip(nip);
		InstansiModel instansi = pegawai.getInstansi();
		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawaiList();

		model.addAttribute("gaji", pegawaiService.hitungGajibyNIP(nip));
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Detail Pegawai");
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansi);
		return "detail-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	public String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = listProvinsi.get(0).getInstansiList();
		System.out.println("masuk            hihihiihi");
		
		List<JabatanModel> listJabatan = jabatanService.findAll();
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addJabatan",})
	public String tambahJabatan(@ModelAttribute PegawaiModel pegawai_new, Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.findAll();
		
		List<InstansiModel> listInstansi = listProvinsi.get(0).getInstansiList();
		
		System.out.println("masuk            hahahha");
		
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("title", "Tambah Pegawai");
		model.addAttribute("pegawai", pegawai);
		return "tambah-pegawai";
	}
	
	

}

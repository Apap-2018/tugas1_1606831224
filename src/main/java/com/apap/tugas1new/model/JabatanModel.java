package com.apap.tugas1new.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "jabatan")
public class JabatanModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name="nama", nullable=false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name="deskripsi", nullable=false)
	private String deskripsi;
	
	@NotNull
	@Column(name = "gaji_pokok", nullable = false )
	private double gajiPokok;
	
	@OneToMany(mappedBy = "jabatan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private List<JabatanPegawaiModel> jabatanPegawaiList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getDeskripsi() {
		return deskripsi;
	}

	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}

	public double getGajiPokok() {
		return gajiPokok;
	}

	public void setGajiPokok(double gajiPokok) {
		this.gajiPokok = gajiPokok;
	}

	public List<JabatanPegawaiModel> getJabatanPegawaiList() {
		return jabatanPegawaiList;
	}

	public void setJabatanPegawaiList(List<JabatanPegawaiModel> jabatanPegawaiList) {
		this.jabatanPegawaiList = jabatanPegawaiList;
	}


}

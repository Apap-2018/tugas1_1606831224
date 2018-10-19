package com.apap.tugas1new.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name= "jabatan_pegawai")
public class JabatanPegawaiModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "id_pegawai", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private PegawaiModel pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "id_jabatan", referencedColumnName = "id", nullable = false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private JabatanModel jabatan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PegawaiModel getPegawai() {
		return pegawai;
	}

	public void setPegawai(PegawaiModel pegawai) {
		this.pegawai = pegawai;
	}

	public JabatanModel getJabatan() {
		return jabatan;
	}

	public void setJabatan(JabatanModel jabatan) {
		this.jabatan = jabatan;
	}


	
}

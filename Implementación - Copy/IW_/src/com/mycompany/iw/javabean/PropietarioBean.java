package com.mycompany.iw.javabean;

import java.io.Serializable;
import java.sql.Date;

public class PropietarioBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id = null;
	private String nombreClub;
	private String nombrePropietario;
	private String localizacion;
	private String email;
	private long telefono;
	private String contrase�a;
	private boolean alta;

	public PropietarioBean(String nombreClub, String nombrePropietario, String localizacion, String email, Long telefono, String contrase�a, boolean alta) {
		this.nombreClub = nombreClub;
		this.nombrePropietario = nombrePropietario;
		this.localizacion = localizacion;
		this.email = email;
		this.telefono = telefono;
		this.contrase�a = contrase�a;
		this.alta = alta;
		
	}
	
	public PropietarioBean(String nombreClub, String nombrePropietario,String email, Long telefono) {
		this.nombreClub = nombreClub;
		this.nombrePropietario = nombrePropietario;
		this.email = email;
		this.telefono = telefono;
		
	}
	
	public PropietarioBean() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreClub() {
		return nombreClub;
	}

	public void setNombreClub(String nombreClub) {
		this.nombreClub = nombreClub;
	}

	public String getNombrePropietario() {
		return nombrePropietario;
	}

	public void setNombrePropietario(String nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getTelefono() {
		return telefono;
	}

	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}

	public String getContrase�a() {
		return contrase�a;
	}

	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}

	public boolean isAlta() {
		return alta;
	}

	public void setAlta(boolean alta) {
		this.alta = alta;
	}
	
	
}

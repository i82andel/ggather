/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.iw;

import java.time.LocalTime;

/**
 *
 * @author crist
 */
public class Pista {
    
    private int id;
    private String deporte;
    private LocalTime horarioInicio;
    private LocalTime horarioFin;
    private float precioHora;
    private float puntuacionMedia;
    private Propietario propietario;
    
    public Pista() {
    }

    public Pista(int id, String deporte, LocalTime horarioInicio, LocalTime horarioFin, float precioHora, float puntuacionMedia, Propietario propietario) {
        this.id = id;
        this.deporte = deporte;
        this.horarioInicio = horarioInicio;
        this.horarioFin = horarioFin;
        this.precioHora = precioHora;
        this.puntuacionMedia = puntuacionMedia;
        this.propietario = propietario;
    }

    public int getId() {
        return id;
    }

    public String getDeporte() {
        return deporte;
    }

    public LocalTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalTime getHorarioFin() {
        return horarioFin;
    }

    public float getPrecioHora() {
        return precioHora;
    }

    public float getPuntuacionMedia() {
        return puntuacionMedia;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public void setHorarioInicio(LocalTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public void setHorarioFin(LocalTime horarioFin) {
        this.horarioFin = horarioFin;
    }

    public void setPrecioHora(float precioHora) {
        this.precioHora = precioHora;
    }

    public void setPuntuacionMedia(float puntuacionMedia) {
        this.puntuacionMedia = puntuacionMedia;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    
    
    
    
}

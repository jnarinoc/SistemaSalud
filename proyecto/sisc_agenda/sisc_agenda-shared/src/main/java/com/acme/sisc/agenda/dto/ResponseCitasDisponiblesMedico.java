/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rasm
 */
public class ResponseCitasDisponiblesMedico implements  Serializable{
    
    private Long   idMedico;
    private String tipoIdentificacion;
    private String numeroIdentificacion;
    private String correoElectronico;
    private String nombreCompleto;
    private String fotografia;    
    private List<CitaDisponible> citasDisponibles;

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public void setNumeroIdentificacion(String numeroIdentificacion) {
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public List<CitaDisponible> getCitasDisponibles() {
        if(citasDisponibles==null){
            citasDisponibles=new ArrayList<>();
        }
        return citasDisponibles;
    }
    

    public void setCitasDisponibles(List<CitaDisponible> citasDisponibles) {
        this.citasDisponibles = citasDisponibles;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
    
    
    
}

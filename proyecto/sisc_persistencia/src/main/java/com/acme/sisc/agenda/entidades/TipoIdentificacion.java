/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.entidades;

/**
 *
 * @author rreedd
 */
public enum TipoIdentificacion {
    CC("Cédula"),
    CE("Cédula de extranjería"),
    TI("T. Identidad"),
    RC("Registro civil"),
    NIT("NIT");
    
    private final String label;
    
    private TipoIdentificacion(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return this.label;
    }
    
}

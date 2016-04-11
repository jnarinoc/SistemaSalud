/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.acme.sisc.agenda.shared.IAgendaLocal;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ws.rs.POST;


import java.util.logging.Logger;

/**
 *
 * @author desarrollador
 */
@Path("medico")
@RequestScoped
public class RestFullAgendaMedico {

    
    private final static Logger _log = Logger.getLogger(RestFullAgendaMedico.class.getName()); 
    
    @Context
    private UriInfo context;
    @EJB
    private IAgendaLocal agenda;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    @Path("/consulta")
    public String consultaAgendaMedico(@DefaultValue("1")
            @QueryParam("page") String   page) {
        
        
        return agenda.consultaAgendaMedico(page);
    }
        
     @GET     
     @Produces(MediaType.APPLICATION_JSON)
     @Path("/citas")
    public String consultarCitasAgendaMedico(
            @QueryParam("idAgenda") String   idAgenda,
             @QueryParam("fecha") String   fecha) {
        
        if(fecha!=null&&!fecha.isEmpty()&&idAgenda!=null&&!idAgenda.isEmpty()){
             return agenda.consultarCitasAgendaMedico(idAgenda, fecha);
        }else{
            return "Datos no validos";
        }
        
       
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/insertarAgenda")
    
    
    public String insertarAgenda(String request){
        _log.log(Level.WARNING," >>> "+ request);
        return "";
    }
}

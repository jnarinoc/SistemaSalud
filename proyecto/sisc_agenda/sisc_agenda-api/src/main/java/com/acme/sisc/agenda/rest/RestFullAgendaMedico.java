/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.rest;

import com.acme.sisc.agenda.dto.DiaAgenda;
import com.acme.sisc.agenda.dto.GeneralResponse;
import com.acme.sisc.agenda.dto.RequestCrearAgenda;
import com.acme.sisc.agenda.entidades.Agenda;
import com.acme.sisc.agenda.entidades.PersonaEps;
import com.acme.sisc.agenda.exceptions.AgendaException;
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
import com.acme.sisc.agenda.util.AgendaUtil;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ws.rs.POST;


import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;

/**
 *
 * @author desarrollador
 */
@Path("medico/agenda")
@RequestScoped
public class RestFullAgendaMedico {

    
    private final static Logger _log = Logger.getLogger(RestFullAgendaMedico.class.getName()); 
    
    @Context
    private UriInfo context;
    @EJB
    private IAgendaLocal agenda;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)    
    @Path("/consultaAgenda")
    public List<Agenda> consultaAgendaMedico(@DefaultValue("0")
            @QueryParam("idMedico")         long   idMedico,
            @QueryParam("fechaInicial")     String fechaInicial,
            @QueryParam("fechaFinal")       String fechaFinal) {
                
        try {
            
            if(fechaInicial!=null&&!fechaInicial.isEmpty()&&
                   fechaFinal!=null&&!fechaFinal.isEmpty() ){
                
                 return agenda.consultaAgendaMedico(idMedico,
                    AgendaUtil.parserStringToDateSimpleDateFormat(fechaInicial),
                    AgendaUtil.parserStringToDateSimpleDateFormat(fechaFinal));
            }else{
                 return agenda.consultaAgendaMedico(idMedico,
                    new Date (),
                    new Date ());
            }
            
            
           
        } catch (AgendaException ex) {
            _log.log(Level.SEVERE, "RestFullAgendaMedico.consultaAgendaMedico", ex);
            return null;
        }
    }
        
     @GET     
     @Produces(MediaType.APPLICATION_JSON)
     @Path("/citas")
    public String consultarCitasAgendaMedico(
            @QueryParam("idAgenda") String   idAgenda,
            @QueryParam("fecha") String   fecha) {
        
        if(fecha!=null&&!fecha.isEmpty()&&idAgenda!=null&&!idAgenda.isEmpty()){
            return null;
//             return agenda.consultarCitasAgendaMedico(idAgenda, fecha);
        }else{
            return "Datos no validos";
        }
        
       
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/nuevaAgenda")    
    public GeneralResponse insertarAgenda(RequestCrearAgenda request){
               
        
        
        _log.log(Level.WARNING," >>> "+ request.getFechaInicio());
        _log.log(Level.WARNING," >>> "+ request.getFechaFinal());
        _log.log(Level.WARNING," >>> "+ request.getCantidadMinutosXCita());
        
        
         List<DiaAgenda>  list=request.getSemana().getListaDias();
        for(DiaAgenda dia:list){
            _log.log(Level.WARNING," >>> "+ dia.getDia());
            _log.log(Level.WARNING," >>> "+ dia.isIncluir());
        }
        return agenda.insertarAgenda(request);
        
        
    }
    
    @GET
     @Path("/{idMedico}/listaEps")
    @Produces(MediaType.APPLICATION_JSON)    
   
    public List<PersonaEps> consultarListaEps(@PathParam("idMedico")  long   idMedico){
      return   agenda.consutarEpsMedico(idMedico);
    }
}

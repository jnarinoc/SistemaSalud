/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.rest;

import com.acme.sisc.agenda.constant.WebConstant;
import com.acme.sisc.agenda.dto.CitaDisponible;
import com.acme.sisc.agenda.dto.GeneralResponse;
import com.acme.sisc.agenda.dto.ResponseCitasDisponiblesMedico;
import com.acme.sisc.agenda.entidades.Cita;
import com.acme.sisc.agenda.shared.ICitaLocal;
import com.acme.sisc.agenda.shared.IUtilitariosAgendaLocal;
import com.acme.sisc.agenda.util.AgendaUtil;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author BryanCFz-user
 */
@Path("paciente")
//@RequestScoped
public class RestFullCitaPaciente {

    private final static Logger logi = Logger.getLogger(RestFullAgendaMedico.class.getName());

    @Context
    private UriInfo context;

    @EJB
    ICitaLocal facadeCita;

    @EJB
    IUtilitariosAgendaLocal facadeUtilitariosAgenda;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/{idPaciente}/listaCitas")
//    public List<Cita> CitasDePaciente(
//            @PathParam("idPaciente") Long idPaciente) {
//        List<Cita> lista = facadeCita.listaCitasPaciente(idPaciente).toArray();
//        return facadeCita.listaCitasPaciente(idPaciente);
//    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idPaciente}/listaCitas")
    public Object[] CitasDePaciente(
            @PathParam("idPaciente") Long idPaciente) {
        List<Cita> lista = facadeCita.listaCitasPendientePaciente(idPaciente);
        //return facadeCita.listaCitasPaciente(idPaciente);
        return lista.toArray();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idPaciente}/listaCitasHistorialEPS")
    public Object[] CitasPacienteHistorial(
            @PathParam("idPaciente") Long idPaciente) {
        List<Cita> lista = facadeCita.listaCitasHistorialPacienteEPS(idPaciente);
        //return facadeCita.listaCitasPaciente(idPaciente);
        return lista.toArray();
    }

    //ORIGINAL
//    @POST
//    @Path("/cancelarCita")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String cancelarUnaCitaDePaciente(Cita cita) {
//        logi.log(Level.WARNING,  "Request para cancelar la Cita con id {0}", cita.getIdCita());
//        return facadeCita.cancelarCita(cita); 
//    }
    /**
     *
     * @param idCita
     * @return
     */
    @POST
    @Path("/{idCita}/cancelarCita")
    @Produces(MediaType.APPLICATION_JSON)
    public GeneralResponse cancelarUnaCitaDePaciente(
            @PathParam("idCita") String idCita
    ) {
        logi.log(Level.WARNING, "Request para cancelar la Cita con id {0}", idCita);
        Long idCitaA = Long.valueOf(idCita);
        return facadeCita.cancelarCita1(idCitaA);
    }

    /**
     *
     * @param idCita
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idCita}/consultarCita")
    public Cita consultarCitaId(
            @PathParam("idCita") Long idCita) {
        logi.log(Level.WARNING, "Obtener la cita segun su id: " + idCita);
        return facadeCita.find(idCita);
    }

    /**
     * Crear y Modificar una Cita
     *
     * @param cita
     * @return
     */
    /*@POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response agendarCitaPaciente(Cita cita) {

        if (cita.getIdCita() == null) {
            try {
                //agendar la cita
                facadeCita.agendarCita(cita);
            } catch (CitaException ex) {
                //error al agendar la cita
                ErrorMessage errorMessage = new ErrorMessage();
                errorMessage.setCode(ex.getErrorCode());
                errorMessage.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
                errorMessage.setMessage(ex.getMessage());
                StringWriter errorStackTrace = new StringWriter();
                ex.printStackTrace(new PrintWriter(errorStackTrace));
                errorMessage.setDeveloperMessage(errorStackTrace.toString());
                errorMessage.setLink("www.banco.com/soporte");

                return Response.status(errorMessage.getStatus())
                        .entity(errorMessage)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        } else {
            //modificar la cita
            //facadeCita.modificarCitaPaciente(cita);
        }
        return Response.ok().build();
    }*/
    /**
     * retorna los medicos segun la especialidad
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{descripcion}/listaMedicosEspecialidad")
    public Object[] listaMedicosEspecialidadEPS(
            @PathParam("descripcion") String descripcion, @QueryParam("idEps") String idEps) {
        if (idEps != null) {
            logi.log(Level.WARNING, "SERVICIOrEST: listaMedicos con especialidad: " + descripcion + "\n\n");
            return facadeUtilitariosAgenda.listaEspecialidadMedicosEps(descripcion, idEps).toArray();
        }

        return null;
    }

    /**
     * retorna las especialidades de los medicos
     *
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/especialidadesMedicosEPS")
    public Object[] especialidadesMedicos() {
        logi.log(Level.WARNING, "SERVICIOrEST: ConsultarEspecialidades\n\n");
        return facadeUtilitariosAgenda.especialidadesEps().toArray();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/citasDisponibles")
    public List<ResponseCitasDisponiblesMedico> buscarCitasDisponiblesPaciente(
            @QueryParam("idEspecialidad") long idEspecialidad,
            @QueryParam("idEps") long idEps,
            @DefaultValue("null")
            @QueryParam("fechaBusqueda") String fechaBusqueda) {

        Map<Long, ResponseCitasDisponiblesMedico> response = new HashMap<Long, ResponseCitasDisponiblesMedico>();
         List<ResponseCitasDisponiblesMedico> list =null;
        try {
            List<Cita> listCitas;
            if (fechaBusqueda.equals("null")) {
                listCitas = facadeCita.buscarCitasDisponiblesPaciente(idEspecialidad, idEps, null);
            } else {
                listCitas = facadeCita.buscarCitasDisponiblesPaciente(idEspecialidad, idEps, fechaBusqueda);
            }
            for (Cita cita : listCitas) {

                CitaDisponible e = new CitaDisponible();
                e.setCiudad(cita.getAgenda().getCiudad());
                e.setDireccion(cita.getAgenda().getDireccion());
                e.setEstadoCita(cita.getEstadoCita());
                e.setFecha(AgendaUtil.parserDateToString(cita.getHoraInicio(), WebConstant.SIMPLE_DATE_FORMAT));
                e.setHoraInicio(AgendaUtil.parserDateToString(cita.getHoraInicio(), WebConstant.SIMPLE_DATE_FORMAT_HOUR));
                e.setHoraFin(AgendaUtil.parserDateToString(cita.getHoraFin(), WebConstant.SIMPLE_DATE_FORMAT_HOUR));
                e.setLocalidad(cita.getAgenda().getLocalidad());
                e.setNumeroConsultorio(cita.getAgenda().getNumeroConsultorio());

                if (response.get(cita.getAgenda().getMedicoEps().getPersona().getIdPersona()) != null) {
                    response.get(cita.getAgenda().getMedicoEps().getPersona().getIdPersona()).getCitasDisponibles().add(e);
                } else {
                    ResponseCitasDisponiblesMedico responseCitasDisponiblesMedico = new ResponseCitasDisponiblesMedico();
                    responseCitasDisponiblesMedico.setCorreoElectronico(cita.getAgenda().getMedicoEps().getPersona().getCorreoElectronico());
                    responseCitasDisponiblesMedico.setFotografia(cita.getAgenda().getMedicoEps().getPersona().getFotografia());
                    responseCitasDisponiblesMedico.setIdMedico(cita.getAgenda().getMedicoEps().getPersona().getIdPersona());
                    if (cita.getAgenda().getMedicoEps().getPersona().getNombres() != null) {
                        responseCitasDisponiblesMedico.setNombreCompleto(cita.getAgenda().getMedicoEps().getPersona().getApellidos() != null
                                ? cita.getAgenda().getMedicoEps().getPersona().getNombres() + " " + cita.getAgenda().getMedicoEps().getPersona().getApellidos()
                                : cita.getAgenda().getMedicoEps().getPersona().getNombres());
                    } else {
                        responseCitasDisponiblesMedico.setNombreCompleto(cita.getAgenda().getMedicoEps().getPersona().getApellidos() != null ? cita.getAgenda().getMedicoEps().getPersona().getApellidos() : "");
                    }
                    responseCitasDisponiblesMedico.setNumeroIdentificacion(cita.getAgenda().getMedicoEps().getPersona().getNumeroIdentificacion()+"");
                    responseCitasDisponiblesMedico.setTipoIdentificacion(cita.getAgenda().getMedicoEps().getPersona().getTipoIdentificacion().name());
                    responseCitasDisponiblesMedico.getCitasDisponibles().add(e);

                    response.put(responseCitasDisponiblesMedico.getIdMedico(), responseCitasDisponiblesMedico);
                }

            }
            
            list = new ArrayList<ResponseCitasDisponiblesMedico>(response.values());
            
            
        } catch (NullPointerException e) {

        }
        
        return list;

    }

}

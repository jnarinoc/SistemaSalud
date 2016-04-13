/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc;

import com.acme.sisc.agenda.entidades.Cita;
import com.acme.sisc.agenda.entidades.CitaExamen;
import com.acme.sisc.agenda.entidades.CitaMedicamento;
import com.acme.sisc.agenda.entidades.Examen;
import com.acme.sisc.sisc_hc.shared.IExamenFacadeLocal;
import com.acme.sisc.sisc_hc.shared.IExamenFacadeRemote;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author GABRIEL
 */
@Stateless
public class ExamenFacade implements IExamenFacadeLocal, IExamenFacadeRemote{
    @EJB
    IExamenFacadeRemote facadeExamen;
    
    private static final Logger LOGGER = Logger.getLogger(MedicamentoFacade.class.getName());
    
    @PersistenceContext(unitName = "SistemaSaludPU")
    private EntityManager em;
    
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamenFacade() {
        
    }
    
    @Override
    public Examen find(Object id) {
        return em.find(Examen.class, id);
    }

    @Override
    public List<Examen> findAll() {
        Query q = em.createQuery("SELECT m FROM Examen m");
        return q.getResultList();
    }

    @Override
    public void addExamenCita(List<CitaExamen> listaExamen) {
        try{
            Cita c = em.find(Cita.class, new Long("1"));
            for (int i=0; i<listaExamen.size(); i++) {
                CitaExamen obj = listaExamen.get(i);
                obj.setFechaGeneracion(new Date());
                obj.setExamen(facadeExamen.find(listaExamen.get(i).getExamen().getIdExamen()));
                obj.setCita(c);
                //listaMedicamentos.get(i).setMedicamento(facadeCita.findById(listaMedicamentos.get(i).getCita().getId()));
                em.persist(obj);
            }
        }catch(Exception e){
            LOGGER.log(Level.SEVERE,"No se encontro cliente {0} ", e);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.sisc.agenda.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jamer
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_PERSONA")
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
  @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM Persona p WHERE p.idPersona = :idPersona"),
  @NamedQuery(name = "Persona.findByTipoIdentificacion", query = "SELECT p FROM Persona p WHERE p.tipoIdentificacion = :tipoIdentificacion"),
  @NamedQuery(name = "Persona.findByNumeroIdentificacion", query = "SELECT p FROM Persona p WHERE p.numeroIdentificacion = :numeroIdentificacion"),
  @NamedQuery(name = "Persona.findByIdentificacion", query = "SELECT p FROM Persona p WHERE p.numeroIdentificacion = :numeroIdentificacion AND p.tipoIdentificacion = :tipoIdentificacion")
})
public class Persona implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id_persona")
  private Long idPersona;
  
  @Basic(optional = false)
  @Column(name = "tipo_identificacion")
  private TipoIdentificacion tipoIdentificacion;
  
  @Basic(optional = false)
  @Column(name = "numero_identificacion")
  private long numeroIdentificacion;

  /*
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona")
    private List<Incapacidad> incapacidadList;
  
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPersona")
    private PersonaJuridica personaJuridica;
  
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idPersona")
    private PersonaNatural personaNatural;
   */
  
  @Version
  @Column(name = "VERSION")
  private Long version;

  public Persona() {
  }

  public Persona(Long idPersona) {
    this.idPersona = idPersona;
  }

  public Persona(Long idPersona, TipoIdentificacion tipoIdentificacion, long numeroIdentificacion) {
    this.idPersona = idPersona;
    this.tipoIdentificacion = tipoIdentificacion;
    this.numeroIdentificacion = numeroIdentificacion;
  }

  public Long getIdPersona() {
    return idPersona;
  }

  public void setIdPersona(Long idPersona) {
    this.idPersona = idPersona;
  }

  public TipoIdentificacion getTipoIdentificacion() {
    return tipoIdentificacion;
  }

  public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
    this.tipoIdentificacion = tipoIdentificacion;
  }

  public long getNumeroIdentificacion() {
    return numeroIdentificacion;
  }

  public void setNumeroIdentificacion(long numeroIdentificacion) {
    this.numeroIdentificacion = numeroIdentificacion;
  }

  /*
  @XmlTransient
  public List<Incapacidad> getIncapacidadList() {
    return incapacidadList;
  }

  public void setIncapacidadList(List<Incapacidad> incapacidadList) {
    this.incapacidadList = incapacidadList;
  }

  public PersonaJuridica getPersonaJuridica() {
    return personaJuridica;
  }

  public void setPersonaJuridica(PersonaJuridica personaJuridica) {
    this.personaJuridica = personaJuridica;
  }

  public PersonaNatural getPersonaNatural() {
    return personaNatural;
  }

  public void setPersonaNatural(PersonaNatural personaNatural) {
    this.personaNatural = personaNatural;
  }
  */

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (idPersona != null ? idPersona.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Persona)) {
      return false;
    }
    Persona other = (Persona) object;
    if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "com.acme.sisc.agenda.entidades.Persona[ idPersona=" + idPersona + " ]";
  }

  /**
   * @return the version
   */
  public Long getVersion() {
    return version;
  }

  /**
   * @param version the version to set
   */
  public void setVersion(Long version) {
    this.version = version;
  }

}

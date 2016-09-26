package com.verbena.contabilidad.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Turno implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	
	@OneToOne
	@JoinColumn(name="id")
	Plantilla plantilla;
	
	@Temporal(TemporalType.DATE)
	@Column(length = 50)
	Date fecha;
	
	@Column(nullable = false)
	String turno;
	
	public Turno() {
	}

	public Turno(Plantilla plantilla, Date fecha, String turno) {
		this.plantilla = plantilla;
		this.fecha = fecha;
		this.turno = turno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Turno other = (Turno)obj;
		if(id == null) {
			if(other.id != null)
				return false;
		} else if(!id.equals(other.id))
			return false;
		return true;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the turno
	 */
	public String getTurno() {
		return turno;
	}

	/**
	 * @param turno the turno to set
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}

	/**
	 * @return the plantilla
	 */
	public Plantilla getPlantilla() {
		return plantilla;
	}

	/**
	 * @param plantilla the plantilla to set
	 */
	public void setPlantilla(Plantilla plantilla) {
		this.plantilla = plantilla;
	}

	
}

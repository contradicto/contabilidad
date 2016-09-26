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
public class Importe implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	@Temporal(TemporalType.DATE)
	@Column(length = 50)
	Date fecha;
	
	@Column(nullable = false, length = 100)
	Double cantidad;

	@Column(nullable = false)
	Boolean entrada;
	
	@Column(nullable = false)
	Boolean extra;

	@OneToOne
	@JoinColumn(name="concepto_id")
	Concepto concepto_id;
	
	@Column(nullable = false)
	Boolean efectivo;
	
	public Importe() {
	}

	public Importe(Date fecha, Double cantidad, Boolean extra, Concepto concepto, Boolean efectivo, Boolean entrada) {
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.extra = extra;
		this.concepto_id= concepto;
		this.efectivo = efectivo;
		this.entrada = entrada;
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
		Importe other = (Importe)obj;
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
	 * @return the cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the extra
	 */
	public Boolean getExtra() {
		return extra;
	}

	/**
	 * @param extra the extra to set
	 */
	public void setExtra(Boolean extra) {
		this.extra = extra;
	}

	/**
	 * @return the efectivo
	 */
	public Boolean getEfectivo() {
		return efectivo;
	}

	/**
	 * @param efectivo the efectivo to set
	 */
	public void setEfectivo(Boolean efectivo) {
		this.efectivo = efectivo;
	}

	/**
	 * @return the entrada
	 */
	public Boolean getEntrada() {
		return entrada;
	}

	/**
	 * @param entrada the entrada to set
	 */
	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}

	/**
	 * @return the concepto_id
	 */
	public Concepto getConcepto_id() {
		return concepto_id;
	}

	/**
	 * @param concepto_id the concepto_id to set
	 */
	public void setConcepto_id(Concepto concepto_id) {
		this.concepto_id = concepto_id;
	}



}

package com.verbena.contabilidad.services;

import java.util.List;

import com.verbena.contabilidad.entity.Concepto;

public interface ConceptoService {

	Concepto addConcepto(Concepto concepto);

	List<Concepto> getConceptos();

	void deleteConcepto(Concepto concepto);
	
	public Concepto findByDescripcion(String descripcion);
}

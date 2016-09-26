package com.verbena.contabilidad.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.verbena.contabilidad.entity.Concepto;
import com.verbena.contabilidad.services.ConceptoService;

@Service("conceptoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ConceptoServiceImpl implements ConceptoService {

	@Autowired
	ConceptoDao dao;

	public Concepto addConcepto(Concepto concepto) {
		return dao.save(concepto);
	}

	public List<Concepto> getConceptos() {
		return dao.queryAll();
	}

	public void deleteConcepto(Concepto concepto) {
		dao.delete(concepto);
	}
	
	public Concepto findByDescripcion(String descripcion) {
		return dao.findByDescripcion(descripcion);
	}

}
